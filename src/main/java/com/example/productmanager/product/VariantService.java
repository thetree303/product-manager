package com.example.productmanager.product;

import com.example.productmanager.common.exception.BusinessException;
import com.example.productmanager.common.exception.ResourceNotFoundException;
import com.example.productmanager.inventory.InventoryItemService;
import com.example.productmanager.product.dto.CreateVariantRequest;
import com.example.productmanager.product.dto.UpdateVariantRequest;
import com.example.productmanager.product.dto.VariantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VariantService {
    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;

    private final InventoryItemService inventoryItemService;

    @Transactional
    public Variant create(Long productId, CreateVariantRequest req) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Khong cho them moi bien the vao san pham dang archive
        if (ProductStatus.ARCHIVED.equals(product.getStatus())) {
            throw new BusinessException("Cannot add variant to archived product");
        }

        // Chan trung sku
        if (Boolean.TRUE.equals(variantRepository.existsBySku(req.sku()))) {
            throw new BusinessException("SKU already exists");
        }

        // Tao moi variant
        Variant newVariant = new Variant();
        newVariant.setSku(req.sku());
        newVariant.setName(req.name());
        newVariant.setDescription(req.description());
        newVariant.setPrice(req.price());
        newVariant.setProduct(product);     // Gan variant voi product

        // Luu variant truoc de lay ID
        Variant saved = variantRepository.save(newVariant);

        // ===== Khoi tao inventory item, gan 1-1 di kem ======
        // Mac dinh = true neu khong co danh dau
        boolean tracked = !Boolean.FALSE.equals(req.tracked());
        inventoryItemService.create(
                newVariant,
                tracked,
                req.costPrice()
        );

        return saved;
    }

    @Transactional(readOnly = true)
    public Page<VariantResponse> findAll(Pageable pageable) {
        return variantRepository
                .findAll(pageable)
                .map(VariantResponse::new);
    }

    @Transactional(readOnly = true)
    public VariantResponse findById(Long variantId) {
        Variant res = variantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found"));
        return new VariantResponse(res);
    }

    @Transactional(readOnly = true)
    public VariantResponse findBySku(String sku) {
        Variant res = variantRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found"));
        return new VariantResponse(res);
    }

    @Transactional()
    public VariantResponse update(Long variantId, UpdateVariantRequest req) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found"));

        if (req.sku() != null) {
            // Kiem tra trung sku
            if (!variant.getSku().equals(req.sku()) && variantRepository.existsBySku(req.sku())) {
                throw new BusinessException("SKU already exists");
            }
            variant.setSku(req.sku());
        }

        if (req.name() != null) {
            variant.setName(req.name());
        }

        if (req.description() != null) {
            variant.setDescription(req.description());
        }

        if (req.price() != null) {
            variant.setPrice(req.price());
        }

        variantRepository.save(variant);
        return new VariantResponse(variant);
    }
}

package com.example.productmanager.product;

import com.example.productmanager.common.exception.DuplicateResourceException;
import com.example.productmanager.common.exception.ResourceNotFoundException;
import com.example.productmanager.product.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final VariantRepository variantRepository;
    private final VariantService variantService;

    // Them moi mat hang
    @Transactional
    public ProductResponse create(CreateProductRequest req) {
        // Kiem tra trung SKU
        validatingSku(req.variants());

        // Tao moi va luu san pham
        Product product = new Product();
        product.setName(req.name());
        product.setDescription(req.description());
        if (req.status() != null) {
            product.setStatus(req.status());
        }
        Product savedProduct = productRepository.save(product);

        // Them lan luot cac variant
        List<Variant> variants = new ArrayList<>();
        req.variants().forEach(v -> {
            Variant newVariant = variantService.create(savedProduct.getId(), v);
            variants.add(newVariant);
        });

        savedProduct.setVariants(variants);
        return new ProductResponse(
                savedProduct,
                variants.stream()
                        .map(VariantResponse::new)
                        .collect(Collectors.toList())
        );
    }

    // Helper kiem tra trung SKU trong danh sach variant
    private boolean validatingSku(List<CreateVariantRequest> variants) {
        Set<String> skuSet = new HashSet<>();
        for (CreateVariantRequest variant : variants) {
            if (!skuSet.add(variant.sku())) {
                throw new DuplicateResourceException("SKU duplicated in variant list");
            }
        }
        List<Variant> existing = variantRepository.findBySkuIn(
                variants.stream().map(CreateVariantRequest::sku).toList()
        );
        if (!existing.isEmpty()) {
            throw new DuplicateResourceException("One or more SKUs already exist");
        }
        return true;
    }

    // Lay danh sach tat ca mat hang, su dung truy van 2 buoc tranh N+1
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        // Buoc 1: Truy van toan bo san pham
        Page<Product> page = productRepository.findAll(pageable);
        Collection<Product> products = page.getContent();

        // Lay danh sach cac product id
        Collection<Long> productIds = products.stream().map(Product::getId).toList();

        // Buoc 2: Truy van toan bo variant cua san pham co id trong list tren
        Collection<Variant> variants = variantRepository.findByProductIdIn(productIds);

        // Ghep cac variant vao product tuong ung
        Map<Long, List<VariantResponse>> variantMap = variants.stream()
                .collect(Collectors.groupingBy(v -> v.getProduct().getId(),
                                Collectors.mapping(
                                        VariantResponse::new, Collectors.toList()
                                )
                        )
                );

        return page.map(p -> {
            return new ProductResponse(
                    p,
                    variantMap.getOrDefault(p.getId(), List.of())
            );
        });
    }

    // Lay cu the 1 mat hang
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        List<VariantResponse> variants = variantRepository
                .findByProductId(id)
                .stream()
                .map(VariantResponse::new)
                .toList();

        return new ProductResponse(product, variants);
    }

    // Cap nhat mat hang
    @Transactional
    public ProductResponse update(Long id, UpdateProductRequest req) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (req.name() != null) {
            product.setName(req.name());
        }

        if (req.description() != null) {
            product.setDescription(req.description());
        }

        if (req.status() != null) {
            product.setStatus(req.status());
        }

        Product savedProduct = productRepository.save(product);
        return new ProductResponse(
                savedProduct,
                savedProduct.getVariants()
                        .stream()
                        .map(VariantResponse::new)
                        .toList());
    }
}

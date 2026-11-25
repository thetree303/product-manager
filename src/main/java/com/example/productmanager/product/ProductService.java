package com.example.productmanager.product;

import com.example.productmanager.common.exception.BusinessException;
import com.example.productmanager.product.dto.CreateProductRequest;
import com.example.productmanager.product.dto.DetailedProductResponse;
import com.example.productmanager.product.dto.ProductResponse;
import com.example.productmanager.product.dto.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // Them moi mat hang
    @Transactional
    public ProductResponse create(CreateProductRequest req) {
        Product product = new Product();
        product.setName(req.name());
        product.setDescription(req.description());
        if (req.status() != null) {
            product.setStatus(req.status());
        }

        Product saved = productRepository.save(product);
        return new ProductResponse(saved);
    }

    // Lay danh sach tat ca mat hang
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository
                .findAll(pageable)
                .map(ProductResponse::new);
    }

    // Lay cu the 1 mat hang
    @Transactional(readOnly = true)
    public DetailedProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Product not found"));
        return new DetailedProductResponse(product);
    }

    // Cap nhat mat hang
    @Transactional
    public ProductResponse update(Long id, UpdateProductRequest req) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Product not found"));

        if (req.name() != null) {
            product.setName(req.name());
        }

        if (req.description() != null) {
            product.setDescription(req.description());
        }

        if (req.status() != null) {
            product.setStatus(req.status());
        }

        Product saved = productRepository.save(product);
        return new ProductResponse(saved);
    }
}

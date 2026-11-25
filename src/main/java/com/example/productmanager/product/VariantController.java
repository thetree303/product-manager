package com.example.productmanager.product;

import com.example.productmanager.product.dto.CreateVariantRequest;
import com.example.productmanager.product.dto.UpdateVariantRequest;
import com.example.productmanager.product.dto.VariantResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VariantController {
    private final VariantService variantService;

    @GetMapping("/variants")
    public ResponseEntity<Page<VariantResponse>> findAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(variantService.findAll(pageable));
    }

    @GetMapping("/variants/{id}")
    public ResponseEntity<VariantResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(variantService.findById(id));
    }

    @GetMapping("/variants/sku/{sku}")
    public ResponseEntity<VariantResponse> findBySku(@PathVariable String sku) {
        return ResponseEntity.ok(variantService.findBySku(sku));
    }

    @PostMapping("/products/{productId}/variants")
    public ResponseEntity<VariantResponse> create(
            @PathVariable Long productId,
            @RequestBody @Valid CreateVariantRequest req
    ) {
        VariantResponse res = variantService.create(productId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PatchMapping("/variants/{id}")
    public ResponseEntity<VariantResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateVariantRequest req
    ) {
        VariantResponse res = variantService.update(id, req);
        return ResponseEntity.ok(res);
    }
}

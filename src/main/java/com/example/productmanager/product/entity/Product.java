package com.example.productmanager.product.entity;

import com.example.productmanager.common.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true, length = 2000)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    // Version để triển khai optimistic lock
    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    // Quan hệ 1-N với Variant
    @OneToMany(
            mappedBy = "product",       // Trường của phía Variant quản lý quan hệ
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false
    )
    private List<Variant> variants = new ArrayList<>();     // Danh sách các variants của Product
}

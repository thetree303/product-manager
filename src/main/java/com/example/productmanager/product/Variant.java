package com.example.productmanager.product;

import com.example.productmanager.inventory.InventoryItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "variants")
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ánh xạ N-1 với Product. Variant là bên nắm giữ FK
    @ManyToOne(
            fetch = FetchType.LAZY,    // Chỉ lấy dữ liệu product khi cần
            optional = false           // Quan hệ không được phép null
    )
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;

    // Anh xa 1-1 voi inventory item
    @OneToOne(mappedBy = "variant")
    private InventoryItem inventoryItem;

    @Column(name = "sku", nullable = false, unique = true, length = 50)
    private String sku;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    @PositiveOrZero(message = "Price must not be negative")
    private BigDecimal price;

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
}

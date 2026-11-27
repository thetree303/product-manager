package com.example.productmanager.inventory;

import com.example.productmanager.product.Variant;
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
@Table(name = "inventory_items")
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ánh xạ 1-1 với Variant, là bên giữ FK
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false, unique = true)
    private Variant variant;

    @Column(name = "tracked", nullable = false)
    private boolean tracked = true;

    @Column(name = "cost_price", nullable = false, precision = 19, scale = 2)
    @PositiveOrZero(message = "Cost Price must not be negative")
    private BigDecimal costPrice;

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

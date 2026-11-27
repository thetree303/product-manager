package com.example.productmanager.inventory;

import com.example.productmanager.location.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "inventory_levels",
        // Ràng buộc: Mỗi cặp "location", "inventory_item" chỉ có 1 "inventory_level"
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_inventory_item_location",
                        columnNames = {"inventory_item_id", "location_id"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_location_id",
                        columnList = "location_id"
                )
        }
)
// Kiểm tra ràng buộc ở cấp độ database
@Check(
        constraints = "available >= 0 AND available <= on_hand AND on_hand >= 0"
)
public class InventoryLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "inventory_item_id",
            nullable = false
    )
    private InventoryItem inventoryItem;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "location_id",
            nullable = false
    )
    private Location location;

    @Column(name = "on_hand", nullable = false)
    @Min(value = 0)
    private Long onHand = 0L;

    @Column(name = "available", nullable = false)
    @Min(value = 0)
    private Long available = 0L;

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

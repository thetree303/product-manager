package com.example.productmanager.inventory;

import com.example.productmanager.location.Location;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.Instant;

@Getter
@Immutable
// Không dùng setter vì đây là audit log.
// Sẽ triển khai constructor thủ công
@NoArgsConstructor(access = AccessLevel.PROTECTED)      // No Args Constructor protected để thỏa mãn yêu cầu JPA
@Entity
@Table(
        name = "inventory_adjustments",
        indexes = {
                @Index(
                        name = "idx_adjustment_inventory_item_location_created_at",
                        columnList = "inventory_item_id, location_id, created_at"
                ),
        }
)
public class InventoryAdjustment {
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

    @Column(name = "request_id", nullable = false, unique = true, length = 50)
    private String requestId;

    @Column(name = "delta", nullable = false)
    private Integer delta;

    @Column(name = "on_hand_after", nullable = false)
    private Integer onHandAfter;

    @Column(name = "reason", nullable = false)
    @Enumerated(EnumType.STRING)
    private InventoryAdjustmentReason reason;

    @Column(name = "note", length = 2000)
    private String note;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    // Constructor
    public InventoryAdjustment(
            InventoryItem inventoryItem,
            Location location,
            String requestId,
            Integer delta,
            Integer onHandAfter,
            InventoryAdjustmentReason reason,
            String note
    ) {
        this.inventoryItem = inventoryItem;
        this.location = location;
        this.requestId = requestId;
        this.delta = delta;
        this.onHandAfter = onHandAfter;
        this.reason = reason;
        this.note = note;
    }
}

package com.example.productmanager.location;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Khóa chính được sinh tự động và tự tăng trong DB
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    // Khi đánh dấu unique, DB tự tạo index, không cần phải khai báo index thêm
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true, length = 2000)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    // Lưu enum dưới dạng string (ACTIVE, INACTIVE), tránh làm hỏng dữ liệu khi tạo enum mới
    private LocationStatus status = LocationStatus.ACTIVE;

    @Column(name = "default_location", nullable = false)
    private Boolean defaultLocation = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;
}

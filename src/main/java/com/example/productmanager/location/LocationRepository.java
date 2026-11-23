package com.example.productmanager.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    // Kiem tra code da ton tai chua
    boolean existsByCode(String code);

    // Tim theo code kho hang
    Optional<Location> findByCode(String code);

    // Tim kho hang mac dinh
    Optional<Location> findByDefaultLocationTrue();
}

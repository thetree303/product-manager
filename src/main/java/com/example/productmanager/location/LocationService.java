package com.example.productmanager.location;

import com.example.productmanager.common.exception.BusinessException;
import com.example.productmanager.common.exception.ResourceNotFoundException;
import com.example.productmanager.location.dto.CreateLocationRequest;
import com.example.productmanager.location.dto.LocationResponse;
import com.example.productmanager.location.dto.UpdateLocationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    // Them moi kho hang vao he thong
    @Transactional
    public LocationResponse create(CreateLocationRequest req) {
        // Kiem tra ma trung
        if (locationRepository.existsByCode(req.code()))
            throw new BusinessException("Location code already exists");

        // Neu la kho dau tien duoc them, hoac duoc danh dau la kho mac dinh trong req
        // Danh dau day la kho mac dinh
        boolean isFirstLocation = locationRepository.count() == 0;
        boolean markAsDefault = Boolean.TRUE.equals(req.defaultLocation());

        boolean shouldBeDefault = isFirstLocation || markAsDefault;

        // Truong hop duoc danh dau mac dinh nhung khong phai kho dau tien
        // Go danh dau kho mac dinh hien tai
        if (shouldBeDefault && !isFirstLocation) {
            unsetCurrentDefaultLocation();
        }

        // Tao kho moi va luu
        Location location = new Location();
        location.setCode(req.code());
        location.setName(req.name());
        location.setDescription(req.description());
        location.setDefaultLocation(shouldBeDefault);

        Location savedLocation = locationRepository.save(location);
        return new LocationResponse(savedLocation);
    }

    // Tim tat ca kho hang trong he thong, su dung phan trang
    @Transactional(readOnly = true)
    public Page<LocationResponse> findAll(Pageable pageable) {
        return locationRepository
                .findAll(pageable)
                .map(LocationResponse::new);
    }

    // Tim kho hang theo ID he thong
    @Transactional(readOnly = true)
    public LocationResponse findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));
        return new LocationResponse(location);
    }

    // Tim kho hang theo ma (code) kho hang
    @Transactional(readOnly = true)
    public LocationResponse findByCode(String code) {
        Location location = locationRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));
        return new LocationResponse(location);
    }

    // Cap nhat kho hang
    @Transactional
    public LocationResponse update(Long id, UpdateLocationRequest req) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        if (req.code() != null) {
            // Kiem tra ma kho hang moi co bi trung voi kho hang khac khong
            // Loai tru ban than kho hang minh
            if (!req.code().equals(location.getCode()) && locationRepository.existsByCode(req.code())) {
                throw new BusinessException("Location code already exists");
            }
            location.setCode(req.code());
        }

        if (req.name() != null) {
            if (req.name().isBlank()) throw new BusinessException("Invalid location name provided");
            location.setName(req.name());
        }

        if (req.description() != null) {
            location.setDescription(req.description());
        }

        if (req.status() != null) {
            // Khong cho set kho hang mac dinh hien tai thanh inactive
            if (Boolean.TRUE.equals(location.getDefaultLocation())
                    && LocationStatus.INACTIVE.equals(req.status())) {
                throw new BusinessException("Cannot set default location to inactive");
            }
            location.setStatus(req.status());
        }

        locationRepository.save(location);
        return new LocationResponse(location);
    }

    // Doi kho hang mac dinh
    @Transactional
    public LocationResponse changeDefaultLocation(Long id) {
        Location newDefault = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        // Kiem tra kho hang moi co active khong
        if (!LocationStatus.ACTIVE.equals(newDefault.getStatus())) {
            throw new BusinessException("Cannot change inactive location to default");
        }

        // Neu location da la mac dinh, khong can lam gi
        if (Boolean.TRUE.equals(newDefault.getDefaultLocation())) {
            return new LocationResponse(newDefault);
        }

        // Doi kho hang mac dinh
        unsetCurrentDefaultLocation();
        newDefault.setDefaultLocation(true);
        locationRepository.flush();
        return new LocationResponse(newDefault);
    }

    // Xoa kho hang
    @Transactional
    public LocationResponse deleteById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        // Neu la kho hang mac dinh, khong cho xoa
        if (Boolean.TRUE.equals(location.getDefaultLocation())) {
            throw new BusinessException("Cannot delete default location");
        }

        // Neu la kho hang dang hoat dong (ACTIVE), khong cho xoa
        if (LocationStatus.ACTIVE.equals(location.getStatus())) {
            throw new BusinessException("Cannot delete active location");
        }

        locationRepository.delete(location);
        return new LocationResponse(location);
    }

    // Helper uncheck kho hang mac dinh hien tai
    private void unsetCurrentDefaultLocation() {
        locationRepository.findByDefaultLocationTrue()
                .ifPresent(current -> {
                    current.setDefaultLocation(false);
                    locationRepository.flush();         // Flush de tranh bi loi unique index
                });
    }
}


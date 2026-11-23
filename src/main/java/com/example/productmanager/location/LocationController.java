package com.example.productmanager.location;

import com.example.productmanager.location.dto.CreateLocationRequest;
import com.example.productmanager.location.dto.LocationResponse;
import com.example.productmanager.location.dto.UpdateLocationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<Page<LocationResponse>> findAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(locationService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<LocationResponse> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(locationService.findByCode(code));
    }

    @PostMapping
    public ResponseEntity<LocationResponse> create(
            @Valid @RequestBody CreateLocationRequest req
    ) {
        LocationResponse res = locationService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LocationResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLocationRequest req
    ) {
        LocationResponse res = locationService.update(id, req);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PatchMapping("/{id}/default")
    public ResponseEntity<LocationResponse> setDefault(
            @PathVariable Long id
    ) {
        LocationResponse res = locationService.changeDefaultLocation(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LocationResponse> delete(@PathVariable Long id) {
        LocationResponse res = locationService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}

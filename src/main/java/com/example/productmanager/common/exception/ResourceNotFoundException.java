package com.example.productmanager.common.exception;

// Exception khi tim 1 tai nguyen nhung khong ton tai
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

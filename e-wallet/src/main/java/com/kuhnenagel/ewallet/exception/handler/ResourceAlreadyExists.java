package com.kuhnenagel.ewallet.exception.handler;

public class ResourceAlreadyExists extends RuntimeException {

    public ResourceAlreadyExists(String message) {
        super(message);
    }
}
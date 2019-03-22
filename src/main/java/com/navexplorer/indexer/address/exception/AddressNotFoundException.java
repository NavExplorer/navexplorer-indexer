package com.navexplorer.indexer.address.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

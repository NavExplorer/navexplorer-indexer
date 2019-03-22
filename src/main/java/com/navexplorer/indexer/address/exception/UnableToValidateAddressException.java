package com.navexplorer.indexer.address.exception;

public class UnableToValidateAddressException extends RuntimeException {
    public UnableToValidateAddressException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.navexplorer.indexer.communityfund.exception;

import com.navexplorer.indexer.exception.IndexerException;

public class InvalidAnonDestinationException extends IndexerException {
    public InvalidAnonDestinationException(String message) {
        super(message);
    }

    public InvalidAnonDestinationException(String message, Throwable e) {
        super(message, e);
    }
}

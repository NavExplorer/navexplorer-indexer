package com.navexplorer.indexer.communityfund.exception;

import com.navexplorer.indexer.exception.IndexerException;

public class InvalidVersionException extends IndexerException {
    public InvalidVersionException(String message) {
        super(message);
    }
}

package com.navexplorer.indexer.block.exception;

public class BlockTransactionNotFoundException extends RuntimeException {
    public BlockTransactionNotFoundException(String message) {
        super(message);
    }

    public BlockTransactionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

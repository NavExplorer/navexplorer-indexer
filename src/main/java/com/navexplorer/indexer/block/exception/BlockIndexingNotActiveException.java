package com.navexplorer.indexer.block.exception;

import com.navexplorer.indexer.exception.IndexerException;

public class BlockIndexingNotActiveException extends IndexerException {
    public BlockIndexingNotActiveException(String message) {
        super(message);
    }
}

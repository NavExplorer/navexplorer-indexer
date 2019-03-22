package com.navexplorer.indexer.block.listener;

import com.navexplorer.indexer.address.indexer.AddressIndexer;
import com.navexplorer.indexer.block.event.BlockIndexedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BlockIndexedListener implements ApplicationListener<BlockIndexedEvent> {
    @Autowired
    AddressIndexer addressIndexer;

    @Override
    public void onApplicationEvent(BlockIndexedEvent event) {
        addressIndexer.indexBlock(event.getBlock());
    }
}

package com.navexplorer.indexer.block.listener;

import com.navexplorer.indexer.address.rewinder.AddressRewinder;
import com.navexplorer.indexer.block.event.BlockRewindEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BlockRewindListener implements ApplicationListener<BlockRewindEvent> {
    @Autowired
    AddressRewinder addressRewinder;

    @Override
    public void onApplicationEvent(BlockRewindEvent event) {
        addressRewinder.rewind(event.getBlock());
    }
}

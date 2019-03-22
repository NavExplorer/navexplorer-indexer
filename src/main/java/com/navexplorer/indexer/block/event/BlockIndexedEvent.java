package com.navexplorer.indexer.block.event;

import com.navexplorer.indexer.block.entity.Block;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class BlockIndexedEvent extends ApplicationEvent {
    @Getter
    private Block block;

    public BlockIndexedEvent(Object source, Block block) {
        super(source);
        this.block = block;
    }
}

package com.navexplorer.indexer.block.event;

import com.navexplorer.indexer.block.entity.Block;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class BlockRewindEvent extends ApplicationEvent {
    @Getter
    private Block block;

    public BlockRewindEvent(Object source, Block block) {
        super(source);
        this.block = block;
    }
}

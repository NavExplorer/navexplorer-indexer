package com.navexplorer.indexer.block.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlockSignal {
    String name;
    boolean signalling;

    public BlockSignal(String name, boolean signalling) {
        this.name = name;
        this.signalling = signalling;
    }
}

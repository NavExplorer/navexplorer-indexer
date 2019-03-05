package com.navexplorer.indexer.block.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RedeemedIn {
    @Getter
    String hash;

    @Getter
    Integer height;

    public RedeemedIn(String hash, Integer height) {
        this.hash = hash;
        this.height = height;
    }
}

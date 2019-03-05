package com.navexplorer.indexer.communityfund.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;

@Data
public class BlockCycle {
    private Integer blocksInCycle;
    private Double minQuorum;
    private Voting proposalVoting;
    private Voting paymentVoting;

    @Data
    public static class Voting {
        private Integer cycles;
        private Double accept;
        private Double reject;
    }

    private Long height;

    public BlockCycle() {
        proposalVoting = new Voting();
        paymentVoting = new Voting();
    }

    @JsonGetter("cycle")
    public Integer getCycle() {
        return ((int) Math.ceil(height.intValue() / blocksInCycle)) + 1;
    }

    @JsonGetter("firstBlock")
    public Integer getFirstBlock() {
        return blocksInCycle * (getCycle() - 1);
    }

    @JsonGetter("currentBlock")
    public Integer getCurrentBlock() {
        return height.intValue() - getFirstBlock() + 1;
    }

    @JsonGetter("remainingBlocks")
    public Integer getRemainingBlocks() {
        return blocksInCycle - getCurrentBlock();
    }
}

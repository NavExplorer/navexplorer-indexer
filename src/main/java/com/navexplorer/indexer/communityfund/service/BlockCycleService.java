package com.navexplorer.indexer.communityfund.service;

import com.navexplorer.indexer.block.service.BlockService;
import com.navexplorer.indexer.communityfund.entity.BlockCycle;
import com.navexplorer.indexer.communityfund.properties.CommunityFundProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockCycleService {
    @Autowired
    private CommunityFundProperties properties;

    @Autowired
    private BlockService blockService;

    BlockCycle getBlockCycle() {
        return getBlockCycleForHeight(blockService.getBestBlock().getHeight());
    }

    BlockCycle getBlockCycleForHeight(Long height) {
        BlockCycle blockCycle = new BlockCycle();
        blockCycle.setHeight(height);
        blockCycle.setBlocksInCycle(properties.getBlocksInCycle());
        blockCycle.setMinQuorum(properties.getMinQuorum());
        blockCycle.getProposalVoting().setCycles(properties.getProposalVoting().getCycles());
        blockCycle.getProposalVoting().setAccept(properties.getProposalVoting().getAccept());
        blockCycle.getProposalVoting().setReject(properties.getProposalVoting().getReject());
        blockCycle.getPaymentVoting().setCycles(properties.getPaymentVoting().getCycles());
        blockCycle.getPaymentVoting().setAccept(properties.getPaymentVoting().getAccept());
        blockCycle.getPaymentVoting().setReject(properties.getPaymentVoting().getReject());

        return blockCycle;

    }
}

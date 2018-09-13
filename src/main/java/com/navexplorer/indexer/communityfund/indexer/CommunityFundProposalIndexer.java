package com.navexplorer.indexer.communityfund.indexer;

import com.navexplorer.indexer.communityfund.factory.CommunityFundProposalFactory;
import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.block.entity.BlockTransactionType;
import com.navexplorer.library.communityfund.entity.BlockCycle;
import com.navexplorer.library.communityfund.entity.Proposal;
import com.navexplorer.library.communityfund.entity.ProposalState;
import com.navexplorer.library.communityfund.repository.CommunityFundProposalRepository;

import com.navexplorer.library.communityfund.service.BlockCycleService;
import com.navexplorer.library.navcoin.service.NavcoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityFundProposalIndexer {
    private static final Logger logger = LoggerFactory.getLogger(CommunityFundProposalIndexer.class);

    @Autowired
    private NavcoinService navcoinService;

    @Autowired
    private CommunityFundProposalFactory communityFundProposalFactory;

    @Autowired
    private CommunityFundProposalRepository communityFundProposalRepository;

    @Autowired
    private BlockCycleService blockCycleService;

    public void indexProposal(BlockTransaction transaction) {
        if (transaction.getVersion() != 4) {
            return;
        }

        try {
            Proposal proposal = communityFundProposalFactory.createProposal(
                    navcoinService.getProposal(transaction.getHash()), transaction.getTime()
            );

            communityFundProposalRepository.save(proposal);

            logger.info("Community proposal saved: " + proposal.getHash());
        } catch (Exception e) {
            logger.error("Unable to index community fund proposal for tx: " + transaction.getHash());
            throw e;
        }
    }

    public void updateProposals(BlockTransaction transaction) {
        if (!transaction.getType().equals(BlockTransactionType.STAKING)) {
            return;
        }

        BlockCycle blockCycle = blockCycleService.getBlockCycle();

        updateProposalsByState(ProposalState.PENDING, transaction, blockCycle);

        if (blockCycle.getCurrentBlock().equals(blockCycle.getBlocksInCycle())) {
            updateProposalsByState(ProposalState.PENDING_FUNDS, transaction, blockCycle);
        }
    }

    private void updateProposalsByState(ProposalState state, BlockTransaction transaction, BlockCycle blockCycle) {
        communityFundProposalRepository.findAllByStateOrderByIdDesc(state).forEach(proposal -> {
            communityFundProposalFactory.updateProposal(proposal, navcoinService.getProposal(proposal.getHash()), transaction);
            communityFundProposalRepository.save(proposal);

            logger.info(String.format("Community proposal updated: %s %s", proposal.getState(), proposal.getHash()));
        });
    }
}

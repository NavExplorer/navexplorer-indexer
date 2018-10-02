package com.navexplorer.indexer.communityfund.rewinder;

import com.navexplorer.indexer.communityfund.factory.ProposalFactory;
import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.block.entity.BlockTransactionType;
import com.navexplorer.library.communityfund.entity.Proposal;
import com.navexplorer.library.communityfund.entity.ProposalState;
import com.navexplorer.library.communityfund.repository.ProposalRepository;
import com.navexplorer.library.navcoin.service.NavcoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProposalRewinder {
    private static final Logger logger = LoggerFactory.getLogger(ProposalRewinder.class);

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private NavcoinService navcoinService;

    @Autowired
    private ProposalFactory proposalFactory;

    public void rewindProposal(BlockTransaction transaction) {
        Proposal proposal = proposalRepository.findOneByHash(transaction.getHash());

        if (proposal != null) {
            proposalRepository.delete(proposal);

            logger.info("Community proposal deleted: " + proposal.getHash());
        }
    }


    public void revertUpdatedProposals(BlockTransaction transaction) {
        if (!transaction.getType().equals(BlockTransactionType.STAKING)) {
            return;
        }

        revertProposalsByState(ProposalState.PENDING);
        revertProposalsByState(ProposalState.ACCEPTED);
        revertProposalsByState(ProposalState.REJECTED);
        revertProposalsByState(ProposalState.EXPIRED);
        revertProposalsByState(ProposalState.PENDING_FUNDS);
    }

    private void revertProposalsByState(ProposalState state) {
        proposalRepository.findAllByStateOrderByIdDesc(state).forEach(proposal -> {
            org.navcoin.response.Proposal apiProposal = navcoinService.getProposal(proposal.getHash());
            proposalFactory.updateProposal(proposal, apiProposal);

            // Set approvedOnBlock as it is not always set in the factory
            apiProposal.getApprovedOnBlock();

            // Set expiresOn as as it is not always set in the factory
            if (apiProposal.getExpiresOn() != null) {
                Date expiresOn = new Date();
                expiresOn.setTime(apiProposal.getExpiresOn() * 1000);
                proposal.setExpiresOn(expiresOn);
            } else {
                proposal.setExpiresOn(null);
            }

            proposalRepository.save(proposal);

            logger.info(String.format("Community proposal updated: %s %s", proposal.getState(), proposal.getHash()));
        });
    }
}

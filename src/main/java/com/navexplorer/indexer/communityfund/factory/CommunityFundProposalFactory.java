package com.navexplorer.indexer.communityfund.factory;

import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.block.entity.BlockTransactionProposalVote;
import com.navexplorer.library.communityfund.entity.BlockCycle;
import com.navexplorer.library.communityfund.entity.Proposal;
import com.navexplorer.library.communityfund.entity.ProposalState;
import com.navexplorer.library.communityfund.entity.ProposalVote;
import com.navexplorer.library.communityfund.service.BlockCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommunityFundProposalFactory {
    @Autowired
    private CommunityFundPaymentRequestFactory communityFundPaymentRequestFactory;

    @Autowired
    private BlockCycleService blockCycleService;

    public Proposal createProposal(org.navcoin.response.Proposal apiProposal, Date createdAt) {
        Proposal proposal = new Proposal();
        proposal.setCreatedAt(createdAt);
        proposal.setState(ProposalState.fromId(apiProposal.getState()));
        proposal.setStatus(apiProposal.getStatus());

        return updateProposal(proposal, apiProposal);
    }

    public Proposal updateProposal(Proposal proposal, org.navcoin.response.Proposal apiProposal) {
        proposal.setVersion(apiProposal.getVersion());
        proposal.setHash(apiProposal.getHash());
        proposal.setBlockHash(apiProposal.getBlockHash());
        proposal.setDescription(apiProposal.getDescription());
        proposal.setRequestedAmount(apiProposal.getRequestedAmount());
        proposal.setNotPaidYet(apiProposal.getNotPaidYet());
        proposal.setUserPaidFee(apiProposal.getUserPaidFee());
        proposal.setPaymentAddress(apiProposal.getPaymentAddress());
        proposal.setProposalDuration(apiProposal.getProposalDuration());
        proposal.setApprovedOnBlock(apiProposal.getApprovedOnBlock());
        proposal.setExpiresOn(apiProposal.getExpiresOn());

        return proposal;
    }

    public Proposal updateProposal(Proposal proposal, org.navcoin.response.Proposal apiProposal, BlockTransaction transaction) {
        updateProposal(proposal, apiProposal);

        if (proposal.getState().equals(ProposalState.PENDING)) {
            updateVotes(proposal, transaction);
        }

        if (proposal.getState().equals(ProposalState.ACCEPTED)) {
            updatePaymentRequests(proposal, apiProposal);
        }

        return proposal;
    }

    private void updateVotes(Proposal proposal, BlockTransaction transaction) {
        BlockCycle blockCycle = blockCycleService.getBlockCycleForHeight(transaction.getHeight().longValue());

        ProposalVote latestVotes = proposal.getLatestVotes();

        if (latestVotes == null) {
            latestVotes = new ProposalVote();
            proposal.getProposalVotes().add(latestVotes);
        } else if (blockCycle.getCurrentBlock().equals(1)) {
            latestVotes = new ProposalVote();
            latestVotes.setVotingCycle(proposal.getLatestVotes().getVotingCycle() + 1);
            proposal.getProposalVotes().add(latestVotes);
        }

        BlockTransactionProposalVote blockTransactionProposalVote = transaction.getProposalVoteForHash(proposal.getHash());
        if (blockTransactionProposalVote != null) {
            if (blockTransactionProposalVote.getVote()) {
                latestVotes.setVotesYes(latestVotes.getVotesYes() + 1);
            } else {
                latestVotes.setVotesNo(latestVotes.getVotesNo() + 1);
            }
        }
    }

    private void updatePaymentRequests(Proposal proposal, org.navcoin.response.Proposal apiProposal) {
        if (proposal.getState().equals(ProposalState.ACCEPTED)) {
            proposal.setPaymentRequests(communityFundPaymentRequestFactory.createPaymentRequests(proposal, apiProposal));
        }
    }
}

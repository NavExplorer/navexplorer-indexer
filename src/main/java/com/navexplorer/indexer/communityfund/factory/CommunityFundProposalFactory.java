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
        proposal.setState(ProposalState.fromId(apiProposal.getState()));
        proposal.setStatus(apiProposal.getStatus());
        proposal.setApprovedOnBlock(apiProposal.getApprovedOnBlock());
        proposal.setExpiresOn(apiProposal.getExpiresOn());

        updateVotes(proposal, apiProposal);

        if (proposal.getState().equals(ProposalState.ACCEPTED)) {
            updatePaymentRequests(proposal, apiProposal);
        }

        return proposal;
    }

    private void updateVotes(Proposal proposal, org.navcoin.response.Proposal apiProposal) {
        ProposalVote latestVotes = proposal.getLatestVotes();

        if (latestVotes == null) {
            latestVotes = new ProposalVote();
            latestVotes.setVotingCycle(apiProposal.getVotingCycle());
            proposal.getProposalVotes().add(latestVotes);
        } else if (apiProposal.getVotingCycle() > proposal.getLatestVotes().getVotingCycle()) {
            latestVotes = new ProposalVote();
            latestVotes.setVotingCycle(apiProposal.getVotingCycle());
            proposal.getProposalVotes().add(latestVotes);
        } else if (apiProposal.getVotingCycle() < proposal.getLatestVotes().getVotingCycle()) {
            proposal.getProposalVotes().remove(proposal.getLatestVotes());
            latestVotes = proposal.getLatestVotes();
        }

        latestVotes.setVotesYes(apiProposal.getVotesYes());
        latestVotes.setVotesNo(apiProposal.getVotesNo());
    }

    private void updatePaymentRequests(Proposal proposal, org.navcoin.response.Proposal apiProposal) {
        if (proposal.getState().equals(ProposalState.ACCEPTED)) {
            proposal.setPaymentRequests(communityFundPaymentRequestFactory.createPaymentRequests(proposal, apiProposal));
        }
    }
}

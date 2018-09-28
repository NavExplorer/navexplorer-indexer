package com.navexplorer.indexer.communityfund.factory;

import com.navexplorer.library.communityfund.entity.Proposal;
import com.navexplorer.library.communityfund.entity.ProposalState;
import com.navexplorer.library.communityfund.entity.ProposalVote;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProposalFactory {
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

        if (apiProposal.getApprovedOnBlock() != null) {
            proposal.setApprovedOnBlock(apiProposal.getApprovedOnBlock());
        }

        if (apiProposal.getExpiresOn() != null) {
            Date expiresOn = new Date();
            expiresOn.setTime(apiProposal.getExpiresOn()*1000);
            proposal.setExpiresOn(expiresOn);
        }

        updateVotes(proposal, apiProposal);

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
}

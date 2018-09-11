package com.navexplorer.indexer.communityfund.factory;

import com.navexplorer.library.communityfund.entity.Proposal;
import com.navexplorer.library.communityfund.entity.ProposalState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommunityFundProposalFactory {
    @Autowired
    private CommunityFundPaymentRequestFactory communityFundPaymentRequestFactory;

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
        proposal.setVotesYes(apiProposal.getVotesYes());
        proposal.setVotesNo(apiProposal.getVotesNo());
        proposal.setVotingCycle(apiProposal.getVotingCycle());
        proposal.setApprovedOnBlock(apiProposal.getApprovedOnBlock());
        proposal.setExpiresOn(apiProposal.getExpiresOn());
        proposal.setState(ProposalState.fromId(apiProposal.getState()));
        proposal.setStatus(apiProposal.getStatus());

        if (proposal.getState().equals(ProposalState.ACCEPTED)) {
            proposal.setPaymentRequests(communityFundPaymentRequestFactory.createPaymentRequests(proposal, apiProposal));
        }

        return proposal;
    }
}

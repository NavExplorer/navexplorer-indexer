package com.navexplorer.indexer.communityfund.factory;

import com.navexplorer.library.communityfund.entity.PaymentRequest;
import com.navexplorer.library.communityfund.entity.PaymentRequestState;
import com.navexplorer.library.communityfund.entity.Proposal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityFundPaymentRequestFactory {
    public List<PaymentRequest> createPaymentRequests(Proposal proposal, org.navcoin.response.Proposal apiProposal) {
        List<PaymentRequest> paymentRequests = new ArrayList<>();

        apiProposal.getPaymentRequests().forEach(apiPaymentRequest -> {
            com.navexplorer.library.communityfund.entity.PaymentRequest paymentRequest = proposal.getPaymentRequest(apiPaymentRequest.getHash());
            if (paymentRequest == null) {
                paymentRequest = new com.navexplorer.library.communityfund.entity.PaymentRequest();
            }

            paymentRequest.setVersion(apiPaymentRequest.getVersion());
            paymentRequest.setHash(apiPaymentRequest.getHash());
            paymentRequest.setBlockHash(apiPaymentRequest.getBlockHash());
            paymentRequest.setDescription(apiPaymentRequest.getDescription());
            paymentRequest.setRequestedAmount(apiPaymentRequest.getRequestedAmount());
            paymentRequest.setVotesYes(apiPaymentRequest.getVotesYes());
            paymentRequest.setVotesNo(apiPaymentRequest.getVotesNo());
            paymentRequest.setVotingCycle(apiPaymentRequest.getVotingCycle());
            paymentRequest.setState(PaymentRequestState.fromId(apiPaymentRequest.getState()));
            paymentRequest.setStatus(apiPaymentRequest.getStatus());

            paymentRequests.add(paymentRequest);
        });

        return paymentRequests;
    }
}

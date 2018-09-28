package com.navexplorer.indexer.communityfund.factory;

import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.communityfund.entity.*;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequestFactory {
    public PaymentRequest createPaymentRequest(org.navcoin.response.PaymentRequest apiPaymentRequest, BlockTransaction transaction) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCreatedAt(transaction.getTime());
        paymentRequest.setProposalHash((String) transaction.getAnonDestinationObject().get("h"));

        return updatePaymentRequest(paymentRequest, apiPaymentRequest);
    }

    public PaymentRequest updatePaymentRequest(PaymentRequest paymentRequest, org.navcoin.response.PaymentRequest apiPaymentRequest) {
        paymentRequest.setVersion(apiPaymentRequest.getVersion());
        paymentRequest.setHash(apiPaymentRequest.getHash());
        paymentRequest.setBlockHash(apiPaymentRequest.getBlockHash());
        paymentRequest.setDescription(apiPaymentRequest.getDescription());
        paymentRequest.setRequestedAmount(apiPaymentRequest.getRequestedAmount());
        paymentRequest.setState(PaymentRequestState.fromId(apiPaymentRequest.getState()));
        paymentRequest.setStatus(apiPaymentRequest.getStatus());

        updateVotes(paymentRequest, apiPaymentRequest);

        return paymentRequest;
    }

    private void updateVotes(PaymentRequest paymentRequest, org.navcoin.response.PaymentRequest apiPaymentRequest) {
        PaymentRequestVote latestVotes = paymentRequest.getLatestVotes();

        if (latestVotes == null) {
            latestVotes = new PaymentRequestVote();
            latestVotes.setVotingCycle(apiPaymentRequest.getVotingCycle());
            paymentRequest.getPaymentRequestVotes().add(latestVotes);
        } else if (apiPaymentRequest.getVotingCycle() > paymentRequest.getLatestVotes().getVotingCycle()) {
            latestVotes = new PaymentRequestVote();
            latestVotes.setVotingCycle(apiPaymentRequest.getVotingCycle());
            paymentRequest.getPaymentRequestVotes().add(latestVotes);
        } else if (apiPaymentRequest.getVotingCycle() < paymentRequest.getLatestVotes().getVotingCycle()) {
            paymentRequest.getPaymentRequestVotes().remove(paymentRequest.getLatestVotes());
            latestVotes = paymentRequest.getLatestVotes();
        }

        latestVotes.setVotesYes(apiPaymentRequest.getVotesYes());
        latestVotes.setVotesNo(apiPaymentRequest.getVotesNo());
    }
}

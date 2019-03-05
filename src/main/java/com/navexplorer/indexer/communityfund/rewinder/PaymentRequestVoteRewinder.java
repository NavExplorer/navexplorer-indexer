package com.navexplorer.indexer.communityfund.rewinder;

import com.navexplorer.indexer.communityfund.repository.PaymentRequestVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequestVoteRewinder {
    @Autowired
    PaymentRequestVoteRepository paymentRequestVoteRepository;

    public void rewindPaymentRequestVotes(int height) {
        paymentRequestVoteRepository.deleteAll(paymentRequestVoteRepository.findAllByHeightGreaterThanEqual(height));
    }
}

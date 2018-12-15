package com.navexplorer.indexer.communityfund.indexer;

import com.navexplorer.indexer.communityfund.factory.PaymentRequestVoteFactory;
import com.navexplorer.library.block.entity.Block;
import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.communityfund.entity.PaymentRequestVote;
import com.navexplorer.library.communityfund.repository.PaymentRequestVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentRequestVoteIndexer {
    @Autowired
    private PaymentRequestVoteFactory paymentRequestVoteFactory;

    @Autowired
    private PaymentRequestVoteRepository paymentRequestVoteRepository;

    public void indexPaymentRequestVotes(Block block, List<BlockTransaction> transactions) {
        List<PaymentRequestVote> paymentRequestVotes = paymentRequestVoteFactory.createPaymentRequestVotes(block, transactions);
        if (paymentRequestVotes.size() != 0) {
            paymentRequestVoteRepository.save(paymentRequestVotes);
        }
    }
}

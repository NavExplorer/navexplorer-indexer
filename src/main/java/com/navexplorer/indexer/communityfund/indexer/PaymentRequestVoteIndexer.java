package com.navexplorer.indexer.communityfund.indexer;

import com.navexplorer.indexer.communityfund.factory.PaymentRequestVoteFactory;
import com.navexplorer.indexer.block.entity.Block;
import com.navexplorer.indexer.block.entity.BlockTransaction;
import com.navexplorer.indexer.communityfund.repository.PaymentRequestVoteRepository;
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
        paymentRequestVoteRepository.saveAll(paymentRequestVoteFactory.createPaymentRequestVotes(block, transactions));
    }
}

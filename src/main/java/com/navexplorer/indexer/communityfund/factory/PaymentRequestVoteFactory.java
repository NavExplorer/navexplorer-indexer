package com.navexplorer.indexer.communityfund.factory;

import com.navexplorer.indexer.block.entity.Block;
import com.navexplorer.indexer.block.entity.BlockTransaction;
import com.navexplorer.indexer.communityfund.entity.PaymentRequestVote;
import com.navexplorer.indexer.block.entity.OutputType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentRequestVoteFactory {
    public List<PaymentRequestVote> createPaymentRequestVotes(Block block, List<BlockTransaction> transactions) {
        List<PaymentRequestVote> paymentRequestsVotes = new ArrayList<>();

        transactions.forEach(transaction -> {
            transaction.getOutputs().forEach(output -> {
                if (output.getType().equals(OutputType.PAYMENT_REQUEST_YES_VOTE)) {
                    PaymentRequestVote vote = new PaymentRequestVote();
                    vote.setAddress(block.getStakedBy());
                    vote.setHeight(block.getHeight().intValue());
                    vote.setPaymentRequest(output.getHash());
                    vote.setVote(true);
                    paymentRequestsVotes.add(vote);

                } else if (output.getType().equals(OutputType.PAYMENT_REQUEST_NO_VOTE)) {
                    PaymentRequestVote vote = new PaymentRequestVote();
                    vote.setAddress(block.getStakedBy());
                    vote.setHeight(block.getHeight().intValue());
                    vote.setPaymentRequest(output.getHash());
                    vote.setVote(false);
                    paymentRequestsVotes.add(vote);
                }
            });
        });

        return paymentRequestsVotes;
    }
}

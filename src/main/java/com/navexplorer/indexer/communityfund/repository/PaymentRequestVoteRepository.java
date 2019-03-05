package com.navexplorer.indexer.communityfund.repository;

import com.navexplorer.indexer.communityfund.entity.PaymentRequestVote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories()
public interface PaymentRequestVoteRepository extends MongoRepository<PaymentRequestVote, String> {
    List<PaymentRequestVote> findAllByHeightGreaterThanEqual(int height);
}

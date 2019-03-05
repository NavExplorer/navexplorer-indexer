package com.navexplorer.indexer.communityfund.repository;

import com.navexplorer.indexer.communityfund.entity.PaymentRequest;
import com.navexplorer.indexer.communityfund.entity.PaymentRequestState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories()
public interface PaymentRequestRepository extends MongoRepository<PaymentRequest, String> {
    List<PaymentRequest> findByBlockHash(String blockHash);
    List<PaymentRequest> findAllByStateOrderByIdDesc(PaymentRequestState status);
}
package com.navexplorer.indexer.address.repository;

import com.navexplorer.indexer.address.entity.AddressTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableMongoRepositories()
public interface AddressTransactionRepository extends MongoRepository<AddressTransaction, String> {
    List<AddressTransaction> findByHeight(Long height);

    AddressTransaction findFirstByAddressOrderByHeightDesc(String address);
}

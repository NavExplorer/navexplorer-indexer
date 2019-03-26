package com.navexplorer.indexer.address.repository;

import com.navexplorer.indexer.address.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

@Service
@EnableMongoRepositories()
public interface AddressRepository extends MongoRepository<Address, String> {
    @Query(value="{ 'hash' : ?0 }", fields="{ 'firstname' : 0}")
    Address findByHash(String hash);
}

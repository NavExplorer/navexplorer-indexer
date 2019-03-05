package com.navexplorer.indexer.block.repository;

import com.navexplorer.indexer.block.entity.BlockTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories()
public interface BlockTransactionRepository extends MongoRepository<BlockTransaction, String> {
    List<BlockTransaction> findByBlockHash(String blockHash);
    BlockTransaction findFirstByHash(String hash);
    BlockTransaction findByBlockHashAndStakeIsGreaterThan(String blockHash, Double greaterThan);
    List<BlockTransaction> findByHeight(Long height);
}

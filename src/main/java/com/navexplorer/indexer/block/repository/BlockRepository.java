package com.navexplorer.indexer.block.repository;

import com.navexplorer.indexer.block.entity.Block;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories()
public interface BlockRepository extends MongoRepository<Block, String> {
    Block findByHeight(Long height);
    Block findFirstByOrderByHeightDesc();
    List<Block> findTop10ByOrderByHeightDesc();
}
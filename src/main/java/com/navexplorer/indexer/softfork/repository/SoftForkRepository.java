package com.navexplorer.indexer.softfork.repository;

import com.navexplorer.indexer.softfork.entity.SoftFork;
import com.navexplorer.indexer.softfork.entity.SoftForkState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories()
public interface SoftForkRepository extends MongoRepository<SoftFork, String> {
    List<SoftFork> findAllByStateIsIn(List<SoftForkState> states);

    List<SoftFork> findAllBySignalledToBlock(Long signalledToBlock);
}

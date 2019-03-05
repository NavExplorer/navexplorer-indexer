package com.navexplorer.indexer.communityfund.repository;

import com.navexplorer.indexer.communityfund.entity.CommunityFundStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories()
public interface CommunityFundStatsRepository extends MongoRepository<CommunityFundStats, String> {

}
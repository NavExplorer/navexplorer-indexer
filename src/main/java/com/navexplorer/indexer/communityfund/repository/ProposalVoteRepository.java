package com.navexplorer.indexer.communityfund.repository;

import com.navexplorer.indexer.communityfund.entity.ProposalVote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories()
public interface ProposalVoteRepository extends MongoRepository<ProposalVote, String> {
    List<ProposalVote> findAllByHeightGreaterThanEqual(int height);
}

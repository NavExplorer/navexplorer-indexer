package com.navexplorer.indexer.communityfund.repository;

import com.navexplorer.indexer.communityfund.entity.Proposal;
import com.navexplorer.indexer.communityfund.entity.ProposalState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Date;
import java.util.List;

@EnableMongoRepositories()
public interface ProposalRepository extends MongoRepository<Proposal, String> {
    Proposal findOneByHash(String hash);
    List<Proposal> findAllByStateOrderByIdDesc(ProposalState status);
}

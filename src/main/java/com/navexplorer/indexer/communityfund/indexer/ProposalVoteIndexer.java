package com.navexplorer.indexer.communityfund.indexer;

import com.navexplorer.indexer.communityfund.factory.ProposalVoteFactory;
import com.navexplorer.indexer.block.entity.Block;
import com.navexplorer.indexer.block.entity.BlockTransaction;
import com.navexplorer.indexer.communityfund.repository.ProposalVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalVoteIndexer {
    @Autowired
    private ProposalVoteFactory proposalVoteFactory;

    @Autowired
    private ProposalVoteRepository proposalVoteRepository;

    public void indexProposalVotes(Block block, List<BlockTransaction> transactions) {
        proposalVoteRepository.saveAll(proposalVoteFactory.createProposalVotes(block, transactions));
    }
}

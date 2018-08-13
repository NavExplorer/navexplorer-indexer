package com.navexplorer.indexer.communityfund.indexer;

import com.navexplorer.indexer.communityfund.exception.InvalidAnonDestinationException;
import com.navexplorer.indexer.communityfund.exception.InvalidVersionException;
import com.navexplorer.indexer.communityfund.factory.CommunityFundProposalFactory;
import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.communityfund.entity.CommunityFundProposal;
import com.navexplorer.library.communityfund.repository.CommunityFundProposalRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CommunityFundProposalIndexerTest {
    @InjectMocks
    private CommunityFundProposalIndexer indexer;

    @Mock
    private CommunityFundProposalFactory factory;

    @Mock
    private CommunityFundProposalRepository repository;

    @Test
    public void it_will_only_index_transactions_with_the_correct_version() {
        BlockTransaction transaction = new BlockTransaction();
        transaction.setVersion(3);

        indexer.indexProposal(transaction);

        verify(repository, never()).save(any(CommunityFundProposal.class));
    }

    @Test
    public void it_will_not_save_if_the_anon_destination_is_invalid() {
        BlockTransaction transaction = new BlockTransaction();
        transaction.setVersion(4);

        when(factory.createProposal(transaction)).thenThrow(new InvalidAnonDestinationException("messsage"));

        indexer.indexProposal(transaction);

        verify(repository, never()).save(any(CommunityFundProposal.class));
    }

    @Test
    public void it_will_not_save_if_the_version_is_invalid() {
        BlockTransaction transaction = new BlockTransaction();
        transaction.setVersion(4);

        when(factory.createProposal(transaction)).thenThrow(new InvalidVersionException("messsage"));

        indexer.indexProposal(transaction);

        verify(repository, never()).save(any(CommunityFundProposal.class));
    }

    @Test
    public void it_can_persist_a_valid_proposal() {
        BlockTransaction transaction = new BlockTransaction();
        transaction.setVersion(4);

        CommunityFundProposal proposal = new CommunityFundProposal();

        when(factory.createProposal(transaction)).thenReturn(proposal);

        indexer.indexProposal(transaction);

        verify(repository).save(proposal);
    }
}

package com.navexplorer.indexer.block.listener;

import com.navexplorer.indexer.block.event.BlockIndexedEvent;
import com.navexplorer.indexer.block.event.BlockTransactionIndexedEvent;
import com.navexplorer.indexer.block.indexer.BlockTransactionProposalVoteIndexer;
import com.navexplorer.indexer.block.service.PreviousInputService;
import com.navexplorer.indexer.communityfund.indexer.CommunityFundProposalIndexer;
import com.navexplorer.library.block.entity.Block;
import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.block.entity.BlockTransactionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class BlockTransactionIndexedListenerTest {
    @InjectMocks
    private BlockTransactionIndexedListener blockTransactionIndexedListener;

    @Mock
    private PreviousInputService previousInputService;

    @Mock
    private BlockTransactionProposalVoteIndexer blockProposalVoteIndexer;

    @Mock
    private CommunityFundProposalIndexer communityFundProposalIndexer;

    @Test
    public void it_will_trigger_previous_index_updater() {
        BlockTransaction transaction = new BlockTransaction();

        blockTransactionIndexedListener.onApplicationEvent(new BlockTransactionIndexedEvent(new Object(), transaction));

        verify(previousInputService).updateTransaction(transaction);
    }

    @Test
    public void it_will_trigger_block_proposal_indexer() {
        BlockTransaction transaction = new BlockTransaction();
        transaction.setType(BlockTransactionType.STAKING);

        blockTransactionIndexedListener.onApplicationEvent(new BlockTransactionIndexedEvent(new Object(), transaction));

        verify(blockProposalVoteIndexer).indexProposalVotes(transaction);
    }
}

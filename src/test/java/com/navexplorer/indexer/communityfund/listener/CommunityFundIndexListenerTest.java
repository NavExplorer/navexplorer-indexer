package com.navexplorer.indexer.communityfund.listener;

import com.navexplorer.indexer.block.event.BlockTransactionIndexedEvent;
import com.navexplorer.indexer.communityfund.indexer.CommunityFundProposalIndexer;
import com.navexplorer.library.block.entity.BlockTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CommunityFundIndexListenerTest {
    @InjectMocks
    private CommunityFundIndexListener listener;

    @Mock
    private CommunityFundProposalIndexer indexer;

    @Test
    public void it_will_trigger_the_community_fund_indexer() {
        BlockTransaction transaction = new BlockTransaction();

        listener.onApplicationEvent(new BlockTransactionIndexedEvent(new Object(), transaction));

        verify(indexer).indexProposal(transaction);
    }
}

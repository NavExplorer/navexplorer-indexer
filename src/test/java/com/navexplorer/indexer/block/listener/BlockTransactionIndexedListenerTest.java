package com.navexplorer.indexer.block.listener;

import com.navexplorer.indexer.block.event.BlockTransactionIndexedEvent;
import com.navexplorer.indexer.block.service.PreviousInputService;
import com.navexplorer.indexer.block.entity.Block;
import com.navexplorer.indexer.block.entity.BlockTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class BlockTransactionIndexedListenerTest {
    @InjectMocks
    private BlockTransactionIndexedListener blockTransactionIndexedListener;

    @Mock
    private PreviousInputService previousInputService;

    @Test
    public void it_will_trigger_previous_index_updater() {
        Block block = new Block();
        BlockTransaction transaction = new BlockTransaction();

        blockTransactionIndexedListener.onApplicationEvent(new BlockTransactionIndexedEvent(new Object(), block, transaction));

        verify(previousInputService).updateTransaction(transaction);
    }
}

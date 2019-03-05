package com.navexplorer.indexer.block.indexer;

import com.navexplorer.indexer.block.event.BlockTransactionIndexedEvent;
import com.navexplorer.indexer.block.factory.BlockTransactionFactory;
import com.navexplorer.indexer.block.entity.BlockTransaction;
import com.navexplorer.indexer.block.repository.BlockRepository;
import com.navexplorer.indexer.block.service.BlockTransactionService;
import com.navexplorer.indexer.navcoin.service.NavcoinService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.navcoin.response.Transaction;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class BlockTransactionIndexerTest {
    @InjectMocks
    private BlockTransactionIndexer blockTransactionIndexer;

    @Mock
    private NavcoinService navcoinService;

    @Mock
    private BlockTransactionService blockTransactionService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private BlockTransactionFactory blockTransactionFactory;

    @Mock
    private BlockRepository blockRepository;

    @Test
    public void it_can_index_a_new_block_transaction() {
        String hash = "HASH";
        Transaction apiTransaction = new Transaction();
        BlockTransaction transaction = new BlockTransaction();
        transaction.setHeight(100);

        when(navcoinService.getTransactionByHash(hash)).thenReturn(apiTransaction);
        when(blockTransactionFactory.createTransaction(apiTransaction)).thenReturn(transaction);

        blockTransactionIndexer.indexTransaction(hash);

        verify(blockTransactionService).save(transaction);
        verify(blockRepository).findByHeight(transaction.getHeight().longValue());
        verify(applicationEventPublisher).publishEvent(any(BlockTransactionIndexedEvent.class));
    }
}

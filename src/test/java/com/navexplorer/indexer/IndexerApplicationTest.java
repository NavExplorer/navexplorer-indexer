package com.navexplorer.indexer;

import com.navexplorer.indexer.address.indexer.AddressLabelIndexer;
import com.navexplorer.indexer.block.indexer.BlockIndexer;
import com.navexplorer.indexer.block.rewinder.BlockRewinder;
import com.navexplorer.indexer.communityfund.indexer.PaymentRequestIndexer;
import com.navexplorer.indexer.communityfund.indexer.ProposalIndexer;
import com.navexplorer.indexer.exception.IndexerException;
import com.navexplorer.indexer.softfork.SoftForkImporter;
import com.navexplorer.indexer.zeromq.Subscriber;
import com.navexplorer.indexer.configuration.repository.ConfigurationRepository;
import com.navexplorer.indexer.navcoin.service.NavcoinService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.navcoin.response.Info;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class IndexerApplicationTest {
    @InjectMocks
    private IndexerApplication application;

    @Mock
    private NavcoinService navcoinService;

    @Mock
    private SoftForkImporter softForkImporter;

    @Mock
    private AddressLabelIndexer addressLabelIndexer;

    @Mock
    private BlockRewinder blockRewinder;

    @Mock
    private BlockIndexer blockIndexer;

    @Mock
    private Subscriber subscriber;

    @Mock
    private ProposalIndexer proposalIndexer;

    @Mock
    private PaymentRequestIndexer paymentRequestIndexer;

    @Mock
    private ConfigurationRepository configurationRepository;

    @Test
    public void it_will_import_the_soft_forks_on_run() {
        Info info = new Info();
        info.setBlocks(1000L);

        when(navcoinService.getInfo()).thenReturn(info);
        when(blockIndexer.indexBlocks()).thenThrow(new IndexerException());

        application.run();

        verify(softForkImporter).importSoftForks();
    }

    @Test
    public void it_will_rewind_the_top_10_blocks_on_run() {
        Info info = new Info();
        info.setBlocks(1000L);

        when(navcoinService.getInfo()).thenReturn(info);
        when(blockIndexer.indexBlocks()).thenThrow(new IndexerException());

        application.run();

        verify(blockRewinder).rewindTop10Blocks();
    }

    @Test
    public void it_will_initiate_the_zeromq_subscriber() {
        Info info = new Info();
        info.setBlocks(1000L);

        when(navcoinService.getInfo()).thenReturn(info);

        application.run();

        verify(blockIndexer).indexAllBlocks();
        verify(subscriber).run();
    }
}

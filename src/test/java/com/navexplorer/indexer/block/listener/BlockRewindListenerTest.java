package com.navexplorer.indexer.block.listener;

import com.navexplorer.indexer.address.rewinder.AddressRewinder;
import com.navexplorer.indexer.block.event.BlockRewindEvent;
import com.navexplorer.indexer.block.rewinder.SignalRewinder;
import com.navexplorer.indexer.block.entity.Block;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class BlockRewindListenerTest {
    @InjectMocks
    private BlockRewindListener blockRewindListener;

    @Mock
    private SignalRewinder signalRewinder;

    @Mock
    private AddressRewinder addressRewinder;

    @Test
    public void it_will_trigger_the_signal_indexer() {
        Block block = new Block();

        blockRewindListener.onApplicationEvent(new BlockRewindEvent(new Object(), block));

        verify(signalRewinder).rewindBlock(block);
    }

    @Test
    public void it_will_trigger_the_address_indexer() {
        Block block = new Block();

        blockRewindListener.onApplicationEvent(new BlockRewindEvent(new Object(), block));

        verify(addressRewinder).rewind(block);
    }
}

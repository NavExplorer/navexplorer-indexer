package com.navexplorer.indexer.softfork.service;

import com.navexplorer.indexer.block.entity.Block;
import com.navexplorer.indexer.block.service.BlockService;
import com.navexplorer.indexer.softfork.entity.SoftFork;
import com.navexplorer.indexer.softfork.repository.SoftForkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SoftForkServiceTest {
    @InjectMocks
    private SoftForkService softForkService;

    @Mock
    private SoftForkRepository softForkRepository;

    @Mock
    private BlockService blockService;

    @Test
    public void it_will_return_null_if_there_are_no_blocks() {
        when(blockService.getBestBlock()).thenReturn(null);

        assertThat(softForkService.getAll()).isEqualTo(null);
    }

    @Test
    public void it_will_return_a_List_of_soft_forks() throws Exception {
        ReflectionTestUtils.setField(softForkService, "blocksInCycle", 400, Integer.class);

        SoftFork softFork = new SoftFork();

        Block block = new Block();
        block.setHeight(500L);
        block.setBlockCycle(2);

        when(softForkRepository.findAll()).thenReturn(Arrays.asList(softFork));
        when(blockService.getBestBlock()).thenReturn(block);

        List<SoftFork> softForks = softForkService.getAll();

        assertThat(softForks).isInstanceOf(List.class);
        assertThat(softForks.get(0)).isEqualTo(softFork);
    }

    @Test
    public void it_will_set_the_blocks_in_cycle() throws Exception {
        ReflectionTestUtils.setField(softForkService, "blocksInCycle", 400, Integer.class);

        SoftFork softFork = new SoftFork();

        Block block = new Block();
        block.setHeight(500L);
        block.setBlockCycle(2);

        when(softForkRepository.findAll()).thenReturn(Arrays.asList(softFork));
        when(blockService.getBestBlock()).thenReturn(block);

        List<SoftFork> softForks = softForkService.getAll();

        assertThat(softForks.get(0).getBlocksInCycle()).isEqualTo(400);
    }

    @Test
    public void it_will_set_the_blocks_remaining() throws Exception {
        ReflectionTestUtils.setField(softForkService, "blocksInCycle", 400, Integer.class);

        SoftFork softFork = new SoftFork();

        Block block = new Block();
        block.setHeight(500L);
        block.setBlockCycle(2);

        when(softForkRepository.findAll()).thenReturn(Arrays.asList(softFork));
        when(blockService.getBestBlock()).thenReturn(block);

        List<SoftFork> softForks = softForkService.getAll();

        assertThat(softForks.get(0).getBlocksRemaining()).isEqualTo(300);
    }
}

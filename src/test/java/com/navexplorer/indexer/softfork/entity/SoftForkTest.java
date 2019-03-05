package com.navexplorer.indexer.softfork.entity;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SoftForkTest {
    @Test
    public void it_can_set_and_get_the_id() {
        String id = "SOFTFORK_ID";
        SoftFork softFork = new SoftFork();
        softFork.setId(id);

        assertThat(softFork.getId()).isEqualTo(id);
    }

    @Test
    public void it_can_set_and_get_the_name() {
        String name = "SOFTFORK_NAME";
        SoftFork softFork = new SoftFork();
        softFork.setName(name);

        assertThat(softFork.getName()).isEqualTo(name);
    }

    @Test
    public void it_can_set_and_get_the_signal_bit() {
        Integer signalBit = 10;
        SoftFork softFork = new SoftFork();
        softFork.setSignalBit(signalBit);

        assertThat(softFork.getSignalBit()).isEqualTo(signalBit);
    }

    @Test
    public void it_can_set_and_get_the_state() {
        SoftForkState state = SoftForkState.LOCKED_IN;
        SoftFork softFork = new SoftFork();
        softFork.setState(state);

        assertThat(softFork.getState()).isEqualTo(state);
    }

    @Test
    public void it_can_set_and_get_the_blocks_signalling() {
        Integer blockSignalling = 10;
        SoftFork softFork = new SoftFork();
        softFork.setBlocksSignalling(blockSignalling);

        assertThat(softFork.getBlocksSignalling()).isEqualTo(blockSignalling);
    }

    @Test
    public void it_can_set_and_get_the_signalled_to_block() {
        Long signalledToBlock = 10000L;
        SoftFork softFork = new SoftFork();
        softFork.setSignalledToBlock(signalledToBlock);

        assertThat(softFork.getSignalledToBlock()).isEqualTo(signalledToBlock);
    }

    @Test
    public void it_can_set_and_get_the_locked_in_height() {
        Long lockedInHeight = 10000L;
        SoftFork softFork = new SoftFork();
        softFork.setLockedInHeight(lockedInHeight);

        assertThat(softFork.getLockedInHeight()).isEqualTo(lockedInHeight);
    }

    @Test
    public void it_can_set_and_get_the_activation_height() {
        Long activationHeight = 20000L;
        SoftFork softFork = new SoftFork();
        softFork.setActivationHeight(activationHeight);

        assertThat(softFork.getActivationHeight()).isEqualTo(activationHeight);
    }

    @Test
    public void it_can_set_and_get_the_blocks_in_cycle() {
        Integer blocksInCycle = 20000;
        SoftFork softFork = new SoftFork();
        softFork.setBlocksInCycle(blocksInCycle);

        assertThat(softFork.getBlocksInCycle()).isEqualTo(blocksInCycle);
    }

    @Test
    public void it_can_set_and_get_the_blocks_remaining() {
        Integer blocksRemaining = 500;
        SoftFork softFork = new SoftFork();
        softFork.setBlocksRemaining(blocksRemaining);

        assertThat(softFork.getBlocksRemaining()).isEqualTo(blocksRemaining);
    }

    @Test
    public void it_can_set_and_get_the_previous_cycles() {
        List<Integer> previousCycles = Arrays.asList(10,20,30);

        SoftFork softFork = new SoftFork();
        softFork.setPreviousCycles(previousCycles);

        assertThat(softFork.getPreviousCycles()).isEqualTo(previousCycles);
    }

    @Test
    public void it_can_increment_signalling() {
        Integer blockSignalling = 500;

        SoftFork softFork = new SoftFork();
        softFork.setBlocksSignalling(blockSignalling);

        assertThat(softFork.getBlocksSignalling()).isEqualTo(blockSignalling);
        softFork.incrementSignalling();
        assertThat(softFork.getBlocksSignalling()).isEqualTo(blockSignalling + 1);
    }

    @Test
    public void it_can_start_a_new_cycle() {
        Integer blockSignalling1 = 500;
        Integer blockSignalling2 = 500;

        SoftFork softFork = new SoftFork();
        softFork.setBlocksSignalling(blockSignalling1);

        softFork.startNewCycle();
        assertThat(softFork.getBlocksSignalling()).isEqualTo(0);
        assertThat(softFork.getPreviousCycles().size()).isEqualTo(1);
        assertThat(softFork.getPreviousCycles().get(0)).isEqualTo(blockSignalling1);

        softFork.setBlocksSignalling(blockSignalling2);

        softFork.startNewCycle();
        assertThat(softFork.getBlocksSignalling()).isEqualTo(0);
        assertThat(softFork.getPreviousCycles().size()).isEqualTo(2);
        assertThat(softFork.getPreviousCycles().get(0)).isEqualTo(blockSignalling1);
        assertThat(softFork.getPreviousCycles().get(1)).isEqualTo(blockSignalling2);
    }
}

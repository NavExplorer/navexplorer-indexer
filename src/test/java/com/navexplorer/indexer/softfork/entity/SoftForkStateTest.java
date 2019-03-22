package com.navexplorer.indexer.softfork.entity;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SoftForkStateTest {
    @Test
    public void it_can_identify_non_terminal_states() {
        List<SoftForkState> softForkStates = SoftForkState.getNonTerminalStates();

        assertThat(softForkStates.contains(SoftForkState.DEFINED)).isTrue();
        assertThat(softForkStates.contains(SoftForkState.STARTED)).isTrue();
        assertThat(softForkStates.contains(SoftForkState.LOCKED_IN)).isTrue();
        assertThat(softForkStates.size()).isEqualTo(3);
    }

    @Test
    public void it_has_five_states() {
        assertThat(SoftForkState.DEFINED).isEqualTo(SoftForkState.DEFINED);
        assertThat(SoftForkState.STARTED).isEqualTo(SoftForkState.STARTED);
        assertThat(SoftForkState.LOCKED_IN).isEqualTo(SoftForkState.LOCKED_IN);
        assertThat(SoftForkState.ACTIVE).isEqualTo(SoftForkState.ACTIVE);
        assertThat(SoftForkState.FAILED).isEqualTo(SoftForkState.FAILED);
    }
}

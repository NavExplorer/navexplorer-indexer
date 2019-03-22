package com.navexplorer.indexer.softfork.entity;

import java.util.ArrayList;
import java.util.List;

public enum SoftForkState {
    DEFINED,
    STARTED,
    LOCKED_IN,
    ACTIVE,
    FAILED;

    public static List<SoftForkState> getNonTerminalStates() {
        List<SoftForkState> states = new ArrayList<>();
        states.add(DEFINED);
        states.add(STARTED);
        states.add(LOCKED_IN);

        return states;
    }
}

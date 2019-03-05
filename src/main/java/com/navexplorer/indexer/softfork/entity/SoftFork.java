package com.navexplorer.indexer.softfork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "softFork")
public class SoftFork {
    @NotNull
    @Id
    @JsonIgnore
    String id;

    @Indexed(unique = true)
    String name;

    Integer signalBit;
    SoftForkState state;
    Integer blocksSignalling = 0;

    @JsonIgnore
    Long signalledToBlock;

    Long lockedInHeight;
    Long activationHeight;

    @Transient
    int blocksInCycle;

    @Transient
    int blocksRemaining;

    List<Integer> previousCycles = new ArrayList<>();

    public void incrementSignalling() {
        blocksSignalling = blocksSignalling + 1;
    }

    public void startNewCycle() {
        previousCycles.add(getBlocksSignalling());
        setBlocksSignalling(0);
    }
}

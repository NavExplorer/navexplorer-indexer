package com.navexplorer.indexer.block.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "block")
public class Block {
    @NotNull
    @Id
    String id;

    @NotNull
    @Indexed(unique=true)
    String hash;

    @NotNull
    String merkleRoot;

    @NotNull
    String bits;

    @NotNull
    Long size;

    @NotNull
    Long version;

    @NotNull
    String versionHex;

    @NotNull
    Long nonce;

    @NotNull
    @Indexed(unique=true)
    Long height;

    @NotNull
    @Indexed
    Double difficulty;

    @NotNull
    Long confirmations;

    @NotNull
    @Indexed
    Date created;

    Double stake;
    String stakedBy;

    Double fees = 0.0;
    Double spend = 0.0;
    Double cFundPayout = 0.0;
    Double balance = 0.0;

    Integer transactions;

    @Indexed
    Integer blockCycle;

    @Transient
    boolean best = false;

    @Transient
    String raw;

    public Block(
            String hash,
            String merkleRoot,
            String bits,
            Long size,
            Long version,
            String versionHex,
            Long nonce,
            Long height,
            Double difficulty,
            Long confirmations,
            Date created,
            Integer transactions
    ) {
        this.hash = hash;
        this.merkleRoot = merkleRoot;
        this.bits = bits;
        this.size = size;
        this.version = version;
        this.versionHex = versionHex;
        this.nonce = nonce;
        this.height = height;
        this.difficulty = difficulty;
        this.confirmations = confirmations;
        this.created = created;
        this.transactions = transactions;
    }
}

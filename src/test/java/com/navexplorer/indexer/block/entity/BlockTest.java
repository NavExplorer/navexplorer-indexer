package com.navexplorer.indexer.block.entity;

import com.navexplorer.indexer.block.entity.Block;
import com.navexplorer.indexer.block.entity.BlockSignal;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockTest {
    @Test
    public void it_can_set_and_get_the_block_id() {
        String id = "BLOCK_ID";
        Block block = new Block();
        block.setId(id);

        assertThat(block.getId()).isEqualTo(id);
    }

    @Test
    public void it_can_set_and_get_the_block_hash() {
        String hash = "BLOCK_HASH";
        Block block = new Block();
        block.setHash(hash);

        assertThat(block.getHash()).isEqualTo(hash);
    }

    @Test
    public void it_can_set_and_get_the_block_merkle_root() {
        String merkleRoot = "BLOCK_MERKLE_ROOT";
        Block block = new Block();
        block.setMerkleRoot(merkleRoot);

        assertThat(block.getMerkleRoot()).isEqualTo(merkleRoot);
    }

    @Test
    public void it_can_set_and_get_the_block_bits() {
        String bits = "BLOCK_BITS";
        Block block = new Block();
        block.setBits(bits);

        assertThat(block.getBits()).isEqualTo(bits);
    }

    @Test
    public void it_can_set_and_get_the_block_size() {
        Long size = 768L;
        Block block = new Block();
        block.setSize(size);

        assertThat(block.getSize()).isEqualTo(size);
    }

    @Test
    public void it_can_set_and_get_the_block_version() {
        Long version = 35462552L;
        Block block = new Block();
        block.setVersion(version);

        assertThat(block.getVersion()).isEqualTo(version);
    }

    @Test
    public void it_can_set_and_get_the_block_version_hex() {
        String version = "VERSION_HEX";
        Block block = new Block();
        block.setVersionHex(version);

        assertThat(block.getVersionHex()).isEqualTo(version);
    }

    @Test
    public void it_can_set_and_get_the_block_nonce() {
        Long nonce = 3452L;
        Block block = new Block();
        block.setNonce(nonce);

        assertThat(block.getNonce()).isEqualTo(nonce);
    }

    @Test
    public void it_can_set_and_get_the_block_height() {
        Long height = 10000L;
        Block block = new Block();
        block.setHeight(height);

        assertThat(block.getHeight()).isEqualTo(height);
    }

    @Test
    public void it_can_set_and_get_the_block_difficulty() {
        Double difficulty = 1000000.00;
        Block block = new Block();
        block.setDifficulty(difficulty);

        assertThat(block.getDifficulty()).isEqualTo(difficulty);
    }

    @Test
    public void it_can_set_and_get_the_block_confirmations() {
        Long confirmations = 32L;
        Block block = new Block();
        block.setConfirmations(confirmations);

        assertThat(block.getConfirmations()).isEqualTo(confirmations);
    }

    @Test
    public void it_can_set_and_get_the_block_created_date() {
        Date created = new Date();
        Block block = new Block();
        block.setCreated(created);

        assertThat(block.getCreated()).isEqualTo(created);
    }

    @Test
    public void it_can_set_and_get_the_block_stake() {
        Double stake = 100.45;
        Block block = new Block();
        block.setStake(stake);

        assertThat(block.getStake()).isEqualTo(stake);
    }

    @Test
    public void it_can_set_and_get_the_block_staked_by() {
        String stakedBy = "STAKING_ADDRESS";
        Block block = new Block();
        block.setStakedBy(stakedBy);

        assertThat(block.getStakedBy()).isEqualTo(stakedBy);
    }

    @Test
    public void it_can_set_and_get_the_block_fees() {
        Double fees = 2.34;
        Block block = new Block();
        block.setFees(fees);

        assertThat(block.getFees()).isEqualTo(fees);
    }

    @Test
    public void it_can_set_and_get_the_block_spend() {
        Double spend = 2.34;
        Block block = new Block();
        block.setSpend(spend);

        assertThat(block.getSpend()).isEqualTo(spend);
    }

    @Test
    public void it_can_set_and_get_the_block_transactions() {
        Integer transactions = 10;
        Block block = new Block();
        block.setTransactions(transactions);

        assertThat(block.getTransactions()).isEqualTo(transactions);
    }

    @Test
    public void it_can_set_and_get_the_block_signals() {
        BlockSignal signal1 = new BlockSignal();
        List<BlockSignal> signals = Arrays.asList(signal1, signal1);
        Block block = new Block();
        block.setSignals(signals);

        assertThat(block.getSignals()).isEqualTo(signals);
    }

    @Test
    public void it_can_set_and_get_the_block_cycle() {
        Integer cycle = 4000;
        Block block = new Block();
        block.setBlockCycle(cycle);

        assertThat(block.getBlockCycle()).isEqualTo(cycle);
    }

    @Test
    public void it_can_set_and_get_the_block_best() {
        Block block = new Block();

        assertThat(block.isBest()).isFalse();
        block.setBest(true);
        assertThat(block.isBest()).isTrue();
    }

    @Test
    public void it_can_set_and_get_the_raw_block_Data() {
        String rawData = "RAWDATA";
        Block block = new Block();
        block.setRaw(rawData);

        assertThat(block.getRaw()).isEqualTo(rawData);
    }
}

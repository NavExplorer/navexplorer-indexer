package com.navexplorer.indexer.address.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class AddressTest {
    @Test
    public void it_can_be_constructed_with_no_arguments() {
        Address address = new Address();

        assertThat(address).isInstanceOf(Address.class);
    }

    @Test
    public void it_can_be_constructed_with_the_hash() {
        String hash = "TEST_HASH";

        Address address = new Address(hash);

        assertThat(address).isInstanceOf(Address.class);
        assertThat(address.getHash()).isEqualTo(hash);
    }

    @Test
    public void it_can_get_and_set_the_id() {
        String id = "ADDRESS_ID";

        Address address = new Address();

        address.setId(id);
        assertThat(address.getId()).isEqualTo(id);
    }

    @Test
    public void it_can_account_for_received_coins() {
        Address address = new Address();
        address.setReceived(5000.0);
        address.setReceivedCount(5);
        address.setBalance(1000.0);

        assertThat(address.receive(1000.0)).isEqualTo(address);
        assertThat(address.getReceived()).isEqualTo(6000.0);
        assertThat(address.getReceivedCount()).isEqualTo(6);
        assertThat(address.getBalance()).isEqualTo(2000.0);
    }

    @Test
    public void it_can_account_for_sent_coins() {
        Address address = new Address();
        address.setSent(5000.0);
        address.setSentCount(5);
        address.setBalance(1000.0);

        assertThat(address.send(1000.0)).isEqualTo(address);
        assertThat(address.getSent()).isEqualTo(6000.0);
        assertThat(address.getSentCount()).isEqualTo(6);
        assertThat(address.getBalance()).isEqualTo(0.0);
    }

    @Test
    public void it_can_account_for_staked_coins() {
        Address address = new Address();
        address.setStakedSent(7.0);
        address.setStakedReceived(10.0);
        address.setStaked(3.0);
        address.setStakedCount(1);
        address.setBalance(3.0);

        assertThat(address.stake(7.0, 10.0)).isEqualTo(address);
        assertThat(address.getStakedSent()).isEqualTo(14.0);
        assertThat(address.getStakedReceived()).isEqualTo(20.0);
        assertThat(address.getStaked()).isEqualTo(6.0);
        assertThat(address.getStakedCount()).isEqualTo(2);
        assertThat(address.getBalance()).isEqualTo(6.0);
    }

    @Test
    public void it_can_set_and_get_the_rich_list_position() {
        Long richListPosition = 5L;

        Address address = new Address();
        address.setRichListPosition(richListPosition);

        assertThat(address.getRichListPosition()).isEqualTo(richListPosition);
    }
}

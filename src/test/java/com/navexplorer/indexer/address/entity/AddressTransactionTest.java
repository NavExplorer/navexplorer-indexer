package com.navexplorer.indexer.address.entity;

import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressTransactionTest {
    @Test
    public void it_can_be_constructed_with_no_arguments() {
        AddressTransaction transaction = new AddressTransaction();

        assertThat(transaction).isInstanceOf(AddressTransaction.class);
    }

    @Test
    public void it_can_get_and_set_the_id() {
        String id = "TX_ID";

        AddressTransaction transaction = new AddressTransaction();
        transaction.setId(id);
        assertThat(transaction.getId()).isEqualTo(id);
    }

    @Test
    public void it_can_get_and_set_the_time() {
        Date time = new Date();

        AddressTransaction transaction = new AddressTransaction();
        transaction.setTime(time);
        assertThat(transaction.getTime()).isEqualTo(time);
    }

    @Test
    public void it_can_get_and_set_the_address() {
        String address = "ADDRESS";

        AddressTransaction transaction = new AddressTransaction();
        transaction.setAddress(address);
        assertThat(transaction.getAddress()).isEqualTo(address);
    }

    @Test
    public void it_can_get_and_set_the_type() {
        AddressTransactionType type = AddressTransactionType.SEND;

        AddressTransaction transaction = new AddressTransaction();
        transaction.setType(type);
        assertThat(transaction.getType()).isEqualTo(type);
    }

    @Test
    public void it_can_get_and_set_the_transaction() {
        String tx = "TRANSACTION";

        AddressTransaction transaction = new AddressTransaction();
        transaction.setTransaction(tx);
        assertThat(transaction.getTransaction()).isEqualTo(tx);
    }

    @Test
    public void it_can_get_and_set_the_height() {
        Integer height = 10000;

        AddressTransaction transaction = new AddressTransaction();
        transaction.setHeight(height);
        assertThat(transaction.getHeight()).isEqualTo(height);
    }

    @Test
    public void it_can_get_and_set_the_balance() {
        Double balance = 9999.99;

        AddressTransaction transaction = new AddressTransaction();
        transaction.setBalance(balance);
        assertThat(transaction.getBalance()).isEqualTo(balance);
    }

    @Test
    public void it_can_get_and_set_the_amount_sent() {
        Double sent = 5000.00;

        AddressTransaction transaction = new AddressTransaction();
        transaction.setSent(sent);
        assertThat(transaction.getSent()).isEqualTo(sent);
    }

    @Test
    public void it_can_get_and_set_the_amount_received() {
        Double received = 5000.00;

        AddressTransaction transaction = new AddressTransaction();
        transaction.setReceived(received);
        assertThat(transaction.getReceived()).isEqualTo(received);
    }
}

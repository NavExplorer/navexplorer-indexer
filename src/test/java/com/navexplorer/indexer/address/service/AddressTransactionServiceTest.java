package com.navexplorer.indexer.address.service;

import com.navexplorer.indexer.address.entity.Address;
import com.navexplorer.indexer.address.entity.AddressTransaction;
import com.navexplorer.indexer.address.query.GetTransactionForAddressQuery;
import com.navexplorer.indexer.address.repository.AddressTransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.*;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddressTransactionServiceTest {
    @InjectMocks
    private AddressTransactionService addressTransactionService;

    @Mock
    private AddressTransactionRepository addressTransactionRepository;

    @Mock
    private GetTransactionForAddressQuery getTransactionForAddressQuery;

    @Test
    public void it_can_save_a_transaction() {
        AddressTransaction transaction = new AddressTransaction();

        assertThat(addressTransactionService.save(transaction)).isEqualTo(transaction);

        verify(addressTransactionRepository).save(transaction);
    }

    @Test
    public void it_can_delete_a_transaction() {
        AddressTransaction transaction = new AddressTransaction();

        assertThat(addressTransactionService.delete(transaction)).isEqualTo(transaction);

        verify(addressTransactionRepository).delete(transaction);
    }

    @Test
    public void it_can_return_address_transactions_for_a_transaction_hash() {
        String transactionHash = "TRANSACTION_HASH";

        AddressTransaction transaction1 = new AddressTransaction();
        AddressTransaction transaction2 = new AddressTransaction();
        List<AddressTransaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(addressTransactionRepository.findByTransaction(transactionHash)).thenReturn(transactions);

        assertThat(addressTransactionService.getTransactionAddresses(transactionHash)).isEqualTo(transactions);
    }

    @Test
    public void it_can_return_the_last_transaction_for_an_address() {
        Address address = new Address("Address");
        AddressTransaction transaction = new AddressTransaction();

        when(addressTransactionRepository.findFirstByAddressOrderByHeightDesc(address.getHash())).thenReturn(transaction);

        assertThat(addressTransactionService.getLastTransactionsForAddress(address.getHash())).isEqualTo(transaction);
    }

    @Test
    public void it_can_get_an_ascending_list_of_transactions_for_an_address() {
        String address = "ADDRESS";
        int size = 10;
        List<String> filters = new ArrayList<>();
        String direction = "from";
        String offset = "5b661c26a26cd00657f921e5";

        AddressTransaction addressTransaction1 = new AddressTransaction();
        AddressTransaction addressTransaction2 = new AddressTransaction();
        AddressTransaction addressTransaction3 = new AddressTransaction();
        AddressTransaction addressTransaction4 = new AddressTransaction();

        List<AddressTransaction> expectedTransactions = Arrays.asList(addressTransaction1, addressTransaction2, addressTransaction3, addressTransaction4);

        when(getTransactionForAddressQuery.query(address, size, filters, direction, offset)).thenReturn(expectedTransactions);

        List<AddressTransaction> actualTransactions = addressTransactionService.getTransactionsForAddress(address, size, filters, direction, offset);

        assertThat(actualTransactions).isEqualTo(expectedTransactions);
    }

    @Test
    public void it_can_get_an_descending_list_of_transactions_for_an_address() {
        String address = "ADDRESS";
        int size = 10;
        List<String> filters = new ArrayList<>();
        String direction = "to";
        String offset = "5b661c26a26cd00657f921e5";

        AddressTransaction addressTransaction1 = new AddressTransaction();
        AddressTransaction addressTransaction2 = new AddressTransaction();
        AddressTransaction addressTransaction3 = new AddressTransaction();
        AddressTransaction addressTransaction4 = new AddressTransaction();

        List<AddressTransaction> expectedTransactions = Arrays.asList(addressTransaction1, addressTransaction2, addressTransaction3, addressTransaction4);

        when(getTransactionForAddressQuery.query(address, size, filters, direction, offset)).thenReturn(expectedTransactions);

        List<AddressTransaction> actualTransactions = addressTransactionService.getTransactionsForAddress(address, size, filters, direction, offset);

        assertThat(actualTransactions).isEqualTo(expectedTransactions);
    }
}

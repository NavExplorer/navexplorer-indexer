package com.navexplorer.indexer.address.service;

import com.navexplorer.indexer.address.entity.Address;
import com.navexplorer.indexer.address.entity.AddressTransaction;
import com.navexplorer.indexer.address.repository.AddressTransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddressTransactionServiceTest {
    @InjectMocks
    private AddressTransactionService addressTransactionService;

    @Mock
    private AddressTransactionRepository addressTransactionRepository;

    @Test
    public void it_can_save_a_transaction() {
        AddressTransaction transaction = new AddressTransaction();

        assertThat(addressTransactionService.save(transaction)).isEqualTo(transaction);

        verify(addressTransactionRepository).save(transaction);
    }

    @Test
    public void it_can_delete_a_transaction() {
        AddressTransaction transaction = new AddressTransaction();

        addressTransactionService.delete(transaction);

        verify(addressTransactionRepository).delete(transaction);
    }

    @Test
    public void it_can_return_the_last_transaction_for_an_address() {
        Address address = new Address("Address");
        AddressTransaction transaction = new AddressTransaction();

        when(addressTransactionRepository.findFirstByAddressOrderByHeightDesc(address.getHash())).thenReturn(transaction);

        assertThat(addressTransactionService.getLastTransactionsForAddress(address.getHash())).isEqualTo(transaction);
    }
}

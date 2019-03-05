package com.navexplorer.indexer.address.service;

import com.navexplorer.indexer.address.entity.AddressTransaction;
import com.navexplorer.indexer.address.repository.AddressTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressTransactionService {
    @Autowired
    private AddressTransactionRepository addressTransactionRepository;

    public AddressTransaction save(AddressTransaction addressTransaction) {
        addressTransactionRepository.save(addressTransaction);

        return addressTransaction;
    }

    public void delete(AddressTransaction addressTransaction) {
        addressTransactionRepository.delete(addressTransaction);
    }

    public AddressTransaction getLastTransactionsForAddress(String address) {
        return addressTransactionRepository.findFirstByAddressOrderByHeightDesc(address);
    }
}

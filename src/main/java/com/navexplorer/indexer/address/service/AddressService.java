package com.navexplorer.indexer.address.service;

import com.navexplorer.indexer.address.exception.AddressNotFoundException;
import com.navexplorer.indexer.address.entity.Address;
import com.navexplorer.indexer.address.exception.AddressNotValidException;
import com.navexplorer.indexer.address.exception.UnableToValidateAddressException;
import com.navexplorer.indexer.address.repository.AddressRepository;
import com.navexplorer.indexer.navcoin.service.NavcoinService;
import org.navcoin.response.ValidateAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    NavcoinService navcoinService;

    public Address getAddress(String hash) throws AddressNotValidException {
        try {
            Address address = addressRepository.findByHash(hash);
            if (address == null) {
                address = createNewAddress(hash);
            }

            return address;
        } catch (Exception e) {
            throw new AddressNotFoundException(String.format("Could not find address: %s", hash), e);
        }
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> save(Iterable<Address> addresses) {
        return addressRepository.saveAll(addresses);
    }

    private Address createNewAddress(String hash) throws AddressNotValidException {
        if (!navcoinService.isAddressValid(hash) && !hash.equals("Community Fund")) {
            String message = String.format("The `%s` address is not valid", hash);
            logger.error(message);
            throw new AddressNotValidException(message);
        }

        logger.info("Creating new address: " + hash);
        Address address = new Address(hash);
        addressRepository.save(address);

        return address;
    }
}

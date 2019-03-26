package com.navexplorer.indexer.address.service;

import com.navexplorer.indexer.address.entity.Address;
import com.navexplorer.indexer.address.exception.AddressNotFoundException;
import com.navexplorer.indexer.address.exception.AddressNotValidException;
import com.navexplorer.indexer.address.exception.UnableToValidateAddressException;
import com.navexplorer.indexer.address.repository.AddressRepository;
import com.navexplorer.indexer.navcoin.service.NavcoinService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.navcoin.response.ValidateAddress;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {
    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private NavcoinService navcoinService;

    @Test
    public void get_address_will_create_a_new_address_if_one_doesnt_exist() {
        String hash = "TEST_ADDRESS";

        when(addressRepository.findByHash(hash)).thenReturn(null);
        when(navcoinService.isAddressValid(hash)).thenReturn(true);

        Address actualAddress = addressService.getAddress(hash);

        verify(addressRepository).save(any(Address.class));

        assertThat(actualAddress.getHash()).isEqualTo(hash);
    }

    @Test(expected = AddressNotFoundException.class)
    public void it_will_throw_an_exception_if_an_address_is_not_valid() {
        String hash = "TEST_ADDRESS";

        when(addressRepository.findByHash(hash)).thenReturn(null);
        when(navcoinService.isAddressValid(hash)).thenReturn(false);

        addressService.getAddress(hash);
    }

    @Test
    public void it_can_save_an_address() {
        Address address = new Address();

        addressService.save(address);

        verify(addressRepository).save(address);
    }

    @Test
    public void it_can_save_a_list_of_addresses() {
        Address address = new Address();
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);

        addressService.save(addresses);

        verify(addressRepository).saveAll(addresses);
    }
}

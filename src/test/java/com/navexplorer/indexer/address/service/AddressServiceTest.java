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
    public void get_address_will_set_the_rich_list_position() {
        String hash = "TEST_ADDRESS";
        Address expectedAddress = new Address(hash);

        when(addressRepository.findByHash(hash)).thenReturn(expectedAddress);
        when(addressRepository.getRichListPosition(expectedAddress.getBalance())).thenReturn(10L);

        Address actualAddress = addressService.getAddress(hash);

        assertThat(actualAddress.getHash()).isEqualTo(hash);
        assertThat(actualAddress.getRichListPosition()).isEqualTo(11L);
    }

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
    public void it_can_retrieve_the_top_100_addresses() {
        Address address = new Address();
        address.setBalance(10.0);

        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        when(addressRepository.findTop100ByBalanceGreaterThanOrderByBalanceDesc(0.0)).thenReturn(addressList);
        when(addressRepository.getRichListPosition(address.getBalance())).thenReturn(0L);

        List<Address> addresses = addressService.getTop100Addresses();
        assertThat(addresses.get(0).getRichListPosition()).isEqualTo(1);
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

    @Test(expected = UnableToValidateAddressException.class)
    public void it_can_throw_and_exception_when_it_cannot_validate_an_address() {
        String address = "ADDRESS";

        when(navcoinService.getValidateAddressByHash(address)).thenThrow(AddressNotValidException.class);

        addressService.validateAddress("ADDRESS");
    }

    @Test(expected = AddressNotValidException.class)
    public void it_can_throw_an_exception_if_the_address_is_invalid() {
        String address = "ADDRESS";
        ValidateAddress validateAddress = new ValidateAddress();
        validateAddress.setAddress(address);
        validateAddress.setIsvalid(false);

        when(navcoinService.getValidateAddressByHash(address)).thenReturn(validateAddress);

        addressService.validateAddress(address);
    }

    @Test
    public void it_can_return_a_validate_address_object_when_an_address_is_validated() {
        String address = "ADDRESS";
        ValidateAddress validateAddress = new ValidateAddress();
        validateAddress.setAddress(address);
        validateAddress.setIsvalid(true);

        when(navcoinService.getValidateAddressByHash(address)).thenReturn(validateAddress);

        assertThat(addressService.validateAddress(address)).isEqualTo(validateAddress);
    }
}

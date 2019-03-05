package com.navexplorer.indexer.navcoin.service;

import com.navexplorer.indexer.navcoin.service.NavcoinService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.navcoin.NavcoinApi;
import org.navcoin.exception.NavcoinException;
import org.navcoin.response.Block;
import org.navcoin.response.Info;
import org.navcoin.response.Transaction;
import org.navcoin.response.ValidateAddress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NavcoinServiceTest {
    @InjectMocks
    private NavcoinService navcoinService;

    @Mock
    private NavcoinApi navcoinApi;

    @Test
    public void it_can_get_info() {
        Info info = new Info();

        when(navcoinApi.getInfo()).thenReturn(info);

        assertThat(navcoinService.getInfo()).isEqualTo(info);
    }

    @Test
    public void it_can_get_the_best_block_hash() {
        String bestBlockHash = "BEST_BLOCK_HASH";

        when(navcoinApi.getBestBlockHash()).thenReturn(bestBlockHash);

        assertThat(navcoinService.getBestBlockHash()).isEqualTo(bestBlockHash);
    }

    @Test
    public void it_can_get_a_block_by_height() {
        Long height = 10000L;
        String hash = "HASH";

        Block block = new Block();
        block.setHeight(10000L);
        block.setHash("HASH");

        when(navcoinApi.getBlockHash(height.intValue())).thenReturn(hash);
        when(navcoinApi.getBlock(hash)).thenReturn(block);

        assertThat(navcoinService.getBlockByHeight(height)).isEqualTo(block);
    }

    @Test
    public void it_will_return_null_if_there_is_not_block_by_height_found() {
        when(navcoinApi.getBlockHash(any())).thenThrow(new NavcoinException());

        assertThat(navcoinService.getBlockByHeight(1000L)).isEqualTo(null);
    }

    @Test
    public void it_can_get_a_block_by_hash() {
        String hash = "HASH";

        Block block = new Block();
        block.setHash("HASH");

        when(navcoinApi.getBlock(hash)).thenReturn(block);

        assertThat(navcoinService.getBlockByHash(hash)).isEqualTo(block);
    }

    @Test
    public void it_will_return_null_if_there_is_not_block_by_hash_found() {
        when(navcoinApi.getBlock(any())).thenThrow(new NavcoinException());

        assertThat(navcoinService.getBlockByHash("HASH")).isEqualTo(null);
    }

    @Test
    public void it_can_get_a_transaction_by_hash() {
        String hash = "HASH";

        Transaction transaction = new Transaction();
        transaction.setHash("HASH");

        when(navcoinApi.getTransaction(hash)).thenReturn(transaction);

        assertThat(navcoinService.getTransactionByHash(hash)).isEqualTo(transaction);
    }

    @Test
    public void it_can_identify_a_valid_address() {
        String hash = "ADDRESS";
        ValidateAddress validateAddress = new ValidateAddress();
        validateAddress.setIsvalid(true);

        when(navcoinApi.validateAddress(hash)).thenReturn(validateAddress);

        assertThat(navcoinService.isAddressValid(hash)).isEqualTo(true);
    }

    @Test
    public void it_can_identify_an_invalid_address() {
        String hash = "INVALID ADDRESS";
        ValidateAddress validateAddress = new ValidateAddress();
        validateAddress.setIsvalid(false);

        when(navcoinApi.validateAddress(hash)).thenReturn(validateAddress);

        assertThat(navcoinService.isAddressValid(hash)).isEqualTo(false);
    }

    @Test
    public void it_can_get_a_validate_address_object_by_hash() {
        String hash = "ADDRESS";
        ValidateAddress validateAddress = new ValidateAddress();
        validateAddress.setAddress(hash);

        when(navcoinApi.validateAddress(hash)).thenReturn(validateAddress);

        assertThat(navcoinService.getValidateAddressByHash(hash)).isEqualTo(validateAddress);
    }
}

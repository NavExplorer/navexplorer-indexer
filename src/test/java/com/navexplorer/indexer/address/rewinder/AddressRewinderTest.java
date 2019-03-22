package com.navexplorer.indexer.address.rewinder;

import com.navexplorer.indexer.address.transition.AddressTransition;
import com.navexplorer.indexer.address.entity.AddressTransaction;
import com.navexplorer.indexer.address.repository.AddressTransactionRepository;
import com.navexplorer.indexer.block.entity.Block;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AddressRewinderTest {
    @InjectMocks
    AddressRewinder addressRewinder;

    @Mock
    private AddressTransactionRepository addressTransactionRepository;

    @Mock
    AddressTransition addressTransition;

    @Test
    public void it_will_transition_down_for_all_address_transactions() {
        Block block = new Block();
        block.setHeight(1000L);

        AddressTransaction addressTransaction1 = new AddressTransaction();
        addressTransaction1.setAddress("Address 1");
        addressTransaction1.setTransaction("Transaction 1");
        AddressTransaction addressTransaction2 = new AddressTransaction();
        addressTransaction1.setAddress("Address 2");
        addressTransaction1.setTransaction("Transaction 2");
        List<AddressTransaction> addressTransactions = Arrays.asList(addressTransaction1, addressTransaction2);

        when(addressTransactionRepository.findByHeight(block.getHeight())).thenReturn(addressTransactions);

        addressRewinder.rewind(block);

        InOrder inOrder = inOrder(addressTransition, addressTransition);
        inOrder.verify(addressTransition).down(addressTransaction1);
        inOrder.verify(addressTransition).down(addressTransaction2);
    }
}

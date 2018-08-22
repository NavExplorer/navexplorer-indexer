package com.navexplorer.indexer.block.factory;

import com.navexplorer.indexer.block.service.PreviousInputService;
import com.navexplorer.library.block.entity.Input;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.navcoin.response.Transaction;
import org.navcoin.response.transaction.Vin;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class InputFactoryTest {
    @InjectMocks
    private InputFactory inputFactory;

    @Mock
    private PreviousInputService previousInputService;

    @Test
    public void it_can_create_inputs() {
        Transaction apiTransaction = new Transaction();

        Vin vin1 = new Vin();
        vin1.setAddress("VIN1 ADDRESS");
        vin1.setValueSat(100000.0);
        vin1.setTxid("VIN1 TX ID");
        vin1.setVout(1);

        Vin vin2 = new Vin();
        vin2.setAddress("VIN2 ADDRESS");
        vin2.setValueSat(500000.0);
        vin2.setTxid("VIN2 TX ID");
        vin2.setVout(2);

        apiTransaction.setVin((Vin[]) Arrays.asList(vin1, vin2).toArray());

        when(previousInputService.getPreviousOutputAmount(vin1.getTxid(), vin1.getVout())).thenReturn(vin1.getValueSat());
        when(previousInputService.getPreviousOutputAmount(vin2.getTxid(), vin2.getVout())).thenReturn(vin2.getValueSat());

        List<Input> inputs = inputFactory.createInputs(apiTransaction);

        assertThat(inputs.get(0).getAddresses()).isEqualTo(Arrays.asList(vin1.getAddress()));
        assertThat(inputs.get(0).getAmount()).isEqualTo(vin1.getValueSat());
        assertThat(inputs.get(0).getPreviousOutput()).isEqualTo(vin1.getTxid());
        assertThat(inputs.get(0).getIndex()).isEqualTo(vin1.getVout());

        assertThat(inputs.get(1).getAddresses()).isEqualTo(Arrays.asList(vin2.getAddress()));
        assertThat(inputs.get(1).getAmount()).isEqualTo(vin2.getValueSat());
        assertThat(inputs.get(1).getPreviousOutput()).isEqualTo(vin2.getTxid());
        assertThat(inputs.get(1).getIndex()).isEqualTo(vin2.getVout());
    }
}

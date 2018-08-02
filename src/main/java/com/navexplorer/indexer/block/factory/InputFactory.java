package com.navexplorer.indexer.block.factory;

import com.navexplorer.library.block.entity.Input;
import org.navcoin.response.Transaction;
import org.navcoin.response.transaction.Vin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputFactory {
    public static List<Input> createInputs(Transaction apiTransaction) {
        List<Input> inputs = new ArrayList<>();
        Arrays.stream(apiTransaction.getVin()).forEach(i -> inputs.add(createInput(i)));

        return inputs;
    }

    private static Input createInput(Vin vin) {
        Input input = new Input();

        if (vin.getAddress() != null) {
            input.getAddresses().add(vin.getAddress());
        }
        input.setAmount(vin.getValueSat() != null ? vin.getValueSat() : 0.0);
        input.setPreviousOutput(vin.getTxid());
        input.setIndex(vin.getVout());

        return input;
    }
}

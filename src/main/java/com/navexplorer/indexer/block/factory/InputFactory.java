package com.navexplorer.indexer.block.factory;

import com.navexplorer.indexer.block.service.PreviousInputService;
import com.navexplorer.library.block.entity.Input;
import org.navcoin.response.Transaction;
import org.navcoin.response.transaction.Vin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InputFactory {

    @Autowired
    private PreviousInputService previousInputService;

    public List<Input> createInputs(Transaction apiTransaction) {
        List<Input> inputs = new ArrayList<>();
        Arrays.stream(apiTransaction.getVin()).forEach(i -> inputs.add(createInput(i)));

        return inputs;
    }

    private Input createInput(Vin vin) {
        Input input = new Input();

        if (vin.getAddress() != null) {
            input.getAddresses().add(vin.getAddress());
        }

        input.setAmount(previousInputService.getPreviousOutputAmount(vin.getTxid(), vin.getVout()));
        input.setPreviousOutput(vin.getTxid());
        input.setIndex(vin.getVout());

        return input;
    }
}

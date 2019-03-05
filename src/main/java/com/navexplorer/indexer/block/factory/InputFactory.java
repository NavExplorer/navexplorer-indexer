package com.navexplorer.indexer.block.factory;

import com.navexplorer.indexer.block.entity.BlockTransaction;
import com.navexplorer.indexer.block.entity.Input;
import com.navexplorer.indexer.block.entity.Output;
import com.navexplorer.indexer.block.service.BlockTransactionService;
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
    private BlockTransactionService blockTransactionService;

    List<Input> createInputs(Transaction apiTransaction) {
        List<Input> inputs = new ArrayList<>();
        Arrays.stream(apiTransaction.getVin()).forEach(i -> inputs.add(createInput(i)));

        return inputs;
    }

    private Input createInput(Vin vin) {
        Input input = new Input();

        if (vin.getZerocoinspend() != null) {
            input.setZeroCoinSpend(vin.getZerocoinspend());
            input.setAmount(vin.getValue());
        } else if (vin.getAddress() != null) {
            input.getAddresses().add(vin.getAddress());
        }

        if (vin.getTxid() != null && vin.getVout() != null) {
            input.setPreviousOutput(vin.getTxid());
            input.setIndex(vin.getVout());

            BlockTransaction transaction = blockTransactionService.getOneByHash(vin.getTxid());

            Output previousOutput = transaction.getOutput(vin.getVout());
            input.setAmount(previousOutput.getAmount());
            input.setPreviousOutput(transaction.getHash());
            input.setPreviousOutputBlock(transaction.getHeight());
            input.setAddresses(previousOutput.getAddresses());
            if (previousOutput.getType() != null) {
                input.setPreviousOutputType(previousOutput.getType());
            }
        }

        return input;
    }
}

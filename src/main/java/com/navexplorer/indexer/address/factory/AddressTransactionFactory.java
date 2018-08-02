package com.navexplorer.indexer.address.factory;

import com.navexplorer.library.address.entity.*;
import com.navexplorer.library.block.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressTransactionFactory {
    public AddressTransaction create(String address, BlockTransaction blockTransaction) {
        if (blockTransaction.getType().equals(BlockTransactionType.EMPTY)) {
            return null;
        }

        List<Input> inputs = blockTransaction.getInputsByAddress(address);
        List<Output> outputs = blockTransaction.getOutputsByAddress(address);

        AddressTransaction transaction = new AddressTransaction();
        transaction.setAddress(address);
        transaction.setTransaction(blockTransaction.getHash());
        transaction.setHeight(blockTransaction.getHeight());
        transaction.setTime(blockTransaction.getTime());

        if (address.equals("Community Fund")) {
            transaction.setType(AddressTransactionType.COMMUNITY_FUND);
            transaction.setReceived(transaction.getReceived() + outputs.stream().mapToDouble(Output::getAmount).sum());

            return transaction;
        }

        inputs.forEach(input -> transaction.setSent(transaction.getSent() + input.getAmount()));

        outputs.forEach(output -> transaction.setReceived(transaction.getReceived() + output.getAmount()));

        if (transaction.getSent() == 0.0 && transaction.getReceived() == 0.0) {
            return null;
        }

        if (blockTransaction.getInputAmount() < blockTransaction.getOutputAmount()) {
            transaction.setType(AddressTransactionType.STAKING);
        } else {
            Double inputAddress = inputs.stream().mapToDouble(Input::getAmount).sum();
            Double outputAddress = outputs.stream().mapToDouble(Output::getAmount).sum();

            if (inputAddress < outputAddress) {
                transaction.setType(AddressTransactionType.RECEIVE);
            } else {
                transaction.setType(AddressTransactionType.SEND);
            }
        }

        return transaction;
    }
}

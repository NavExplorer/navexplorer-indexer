package com.navexplorer.indexer.block.factory;

import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.block.entity.BlockTransactionType;
import com.navexplorer.library.block.entity.Output;
import com.navexplorer.library.block.entity.OutputType;
import org.navcoin.response.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BlockTransactionFactory {
    @Autowired
    InputFactory inputFactory;

    @Autowired
    OutputFactory outputFactory;

    public BlockTransaction createTransaction(Transaction apiTransaction) {
        BlockTransaction transaction = new BlockTransaction();
        transaction.setHash(apiTransaction.getTxid());
        transaction.setTime(new Date(apiTransaction.getTime() * 1000));
        transaction.setHeight(apiTransaction.getHeight());
        transaction.setBlockHash(apiTransaction.getBlockhash());

        if (!transactionIsCoinbase(apiTransaction)) {
            transaction.setInputs(inputFactory.createInputs(apiTransaction));
        }
        transaction.setOutputs(outputFactory.createOutputs(apiTransaction));
        transaction.setType(applyType(transaction, apiTransaction));

        if (!transactionIsCoinbase(apiTransaction)) {
            transaction.setFees(applyFees(transaction));
            transaction.setStake(applyStaking(transaction));
            transaction.setVersion(apiTransaction.getVersion());
            transaction.setAnonDestination(apiTransaction.getAnonDestination());
        }

        return transaction;
    }

    private BlockTransactionType applyType(BlockTransaction transaction, Transaction apiTransaction) {
        if (transactionIsCoinbase(apiTransaction)) {
            return BlockTransactionType.COINBASE;
        }

        Double outputAmount = transaction.getOutputAmount();
        Double inputAmount = transaction.getInputAmount();

        if (outputAmount - inputAmount > 0) {
            if (transaction.hasOutputOfType(OutputType.COLD_STAKING)) {
                return BlockTransactionType.COLD_STAKING;
            } else {
                return BlockTransactionType.STAKING;
            }
        }

        return BlockTransactionType.SPEND;
    }

    private Double applyFees(BlockTransaction transaction) {
        if (transaction.getInputAmount() - transaction.getOutputAmount() > 0) {
            return transaction.getInputAmount() - transaction.getOutputAmount();
        }

        return 0.0;
    }

    private Double applyStaking(BlockTransaction transaction) {
        if (!transaction.isCoinbase() && transaction.getOutputAmount() - transaction.getInputAmount() > 0) {
            String stakingAddress = transaction.getOutputs().stream()
                    .filter(t -> t.getAddresses().size() != 0)
                    .findFirst().orElse(new Output()).getAddresses().get(0);

            if (!transaction.hasInputWithAddress(stakingAddress)) {
                transaction.getInputs().forEach(i -> i.getAddresses().add(stakingAddress));
            }

            return transaction.getOutputs().stream()
                    .filter(t -> t.getAddresses().contains(stakingAddress))
                    .mapToDouble(Output::getAmount).sum() - transaction.getInputAmount();
        }

        return 0.0;
    }

    private boolean transactionIsCoinbase(Transaction transaction) {
        return transaction.getVin() != null && transaction.getVin().length == 1 && transaction.getVin()[0].getCoinbase() != null;
    }
}

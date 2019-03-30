package com.navexplorer.indexer.block.factory;

import com.navexplorer.indexer.block.entity.BlockTransaction;
import com.navexplorer.indexer.block.entity.BlockTransactionType;
import com.navexplorer.indexer.block.entity.Output;
import com.navexplorer.indexer.block.entity.OutputType;
import org.navcoin.response.Transaction;
import org.navcoin.response.transaction.Vout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
            transaction.setFees(applyFees(transaction, apiTransaction));
            transaction.setVersion(apiTransaction.getVersion());
            transaction.setAnonDestination(apiTransaction.getAnonDestination());
        }
        transaction.setStake(applyStaking(transaction));

        return transaction;
    }

    private BlockTransactionType applyType(BlockTransaction transaction, Transaction apiTransaction) {
        if (transactionIsCoinbase(apiTransaction)) {
            return BlockTransactionType.COINBASE;
        }

        if (apiTransaction.getVersion().equals(131)) {
            Vout firstOutput = apiTransaction.getVout()[0];
            if ((firstOutput.getScriptPubKey() == null || firstOutput.getScriptPubKey().getAsm().equals("")) && firstOutput.getValue() == 0) {
                return BlockTransactionType.PRIVATE_STAKING;
            } else {
                return BlockTransactionType.PRIVATE_SPEND;
            }
        }

        if (transaction.getOutputAmount() - transaction.getInputAmount() > 0) {
            if (transaction.hasOutputOfType(OutputType.COLD_STAKING)) {
                return BlockTransactionType.COLD_STAKING;
            }

            return BlockTransactionType.STAKING;
        }

        return BlockTransactionType.SPEND;
    }

    private Double applyFees(BlockTransaction transaction, Transaction apiTransaction) {
        if (transaction.getType().equals(BlockTransactionType.PRIVATE_SPEND)) {
            for (Vout vout : apiTransaction.getVout()) {
                if (vout.getScriptPubKey() != null && vout.getScriptPubKey().getAsm().equals("OP_FEE")) {
                    return vout.getValueSat();
                }
            }

            return 0.0;
        }

        if (transaction.getInputAmount() - transaction.getOutputAmount() > 0) {
            return transaction.getInputAmount() - transaction.getOutputAmount();
        }

        return 0.0;
    }

    private Double applyStaking(BlockTransaction transaction) {
        if (transaction.isSpend() || transaction.isPrivateSpend()) {
            return 0.0;
        }

        if (transaction.isCoinbase()) {
            for (Output output : transaction.getOutputs()) {
                if (output.getType().equals(OutputType.PUBKEY)) {
                    return output.getAmount();
                }
            }
        } else {
            if (transaction.isPrivateStaking() || transaction.getHeight() >= 2761920) {
                // hard coded to 2 as static rewards arrived after block 2761920 and  before zeroCt
                return 200000000.0;
            }

            if (transaction.getOutputAmount() - transaction.getInputAmount() > 0) {
                Output stakingOutput = transaction.getOutputs().stream()
                        .filter(t -> t.getAddresses().size() != 0)
                        .findFirst().orElse(new Output());

                if (stakingOutput.getAddresses().size() != 0) {
                    String stakingAddress = stakingOutput.getAddresses().get(0);

                    if (!transaction.hasInputWithAddress(stakingAddress)) {
                        transaction.getInputs().forEach(i -> i.getAddresses().add(stakingAddress));
                    }

                    return transaction.getOutputs().stream()
                            .filter(t -> t.getAddresses().contains(stakingAddress))
                            .mapToDouble(Output::getAmount).sum() - transaction.getInputAmount();
                }
            }
        }

        return 0.0;
    }

    private boolean transactionIsCoinbase(Transaction transaction) {
        return transaction.getVin() != null && transaction.getVin().length == 1 && transaction.getVin()[0].getCoinbase() != null;
    }
}

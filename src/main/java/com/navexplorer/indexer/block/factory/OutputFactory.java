package com.navexplorer.indexer.block.factory;

import com.navexplorer.indexer.block.entity.OutputType;
import com.navexplorer.indexer.block.entity.Output;
import org.navcoin.response.Transaction;
import org.navcoin.response.transaction.Vout;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OutputFactory {
    List<Output> createOutputs(Transaction apiTransaction) {
        List<Output> outputs = new ArrayList<>();
        Arrays.stream(apiTransaction.getVout()).forEach(o -> outputs.add(createOutput(o)));

        return outputs;
    }

    private Output createOutput(Vout vout) {
        Output output = new Output();
        output.setIndex(vout.getN().intValue());

        if (vout.getScriptPubKey() != null) {
            output.setType(OutputType.fromValue(vout.getScriptPubKey().getType()));
            output.setAmount(vout.getValueSat());
            output.setAddresses(vout.getScriptPubKey().getAddresses());

            List<String> votingTypes = Arrays.asList(
                    OutputType.PROPOSAL_YES_VOTE.value(),
                    OutputType.PROPOSAL_NO_VOTE.value(),
                    OutputType.PAYMENT_REQUEST_YES_VOTE.value(),
                    OutputType.PAYMENT_REQUEST_NO_VOTE.value());

            if (votingTypes.contains(vout.getScriptPubKey().getType())) {
                output.setHash(vout.getScriptPubKey().getHash());
            }
        }

        return output;
    }
}

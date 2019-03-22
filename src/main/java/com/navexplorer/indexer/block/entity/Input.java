package com.navexplorer.indexer.block.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Input {
    Integer index;
    List<String> addresses;
    Double amount = 0.0;
    String previousOutput;
    Integer previousOutputBlock;
    OutputType previousOutputType;
    String zeroCoinSpend;

    public List<String> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<>();
        }

        return addresses;
    }

    public boolean hasAddress(String address) {
        return getAddresses().size() != 0 && getAddresses().stream().anyMatch(a -> a.equals(address));
    }
}

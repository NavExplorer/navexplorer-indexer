package com.navexplorer.indexer.block.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Output {
    Integer index;
    OutputType type;
    List<String> addresses;
    Double amount = 0.0;
    RedeemedIn redeemedIn;
    String hash;

    public List<String> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<>();
        }

        return addresses;
    }

    public boolean hasAddress(String address) {
        return getAddresses().size() != 0 && getAddresses().stream().anyMatch(a -> a.equals(address));
    }

    public boolean isColdStaking() {
        return type != null && type.equals(OutputType.COLD_STAKING);
    }
}

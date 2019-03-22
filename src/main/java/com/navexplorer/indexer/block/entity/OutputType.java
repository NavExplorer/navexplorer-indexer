package com.navexplorer.indexer.block.entity;

public enum OutputType {
    NONSTANDARD("nonstandard"),
    NULLDATA("nulldata"),
    PUBKEYHASH("pubkeyhash"),
    PUBKEY("pubkey"),
    SCRIPTHASH("scripthash"),
    COLD_STAKING("cold_staking"),
    CFUND_CONTRIBUTION("cfund_contribution"),
    PROPOSAL_NO_VOTE("proposal_no_vote"),
    PROPOSAL_YES_VOTE("proposal_yes_vote"),
    PAYMENT_REQUEST_NO_VOTE("payment_request_no_vote"),
    PAYMENT_REQUEST_YES_VOTE("payment_request_yes_vote"),
    PRIVATE_TRANSACTION("private_transaction");

    String value;

    OutputType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static OutputType fromValue(String text) {
        for (OutputType outputType : OutputType.values()) {
            if (outputType.value.equalsIgnoreCase(text)) {
                return outputType;
            }
        }

        System.out.println("Output Type not recognised: " + text);
        System.exit(1);

        return null;
    }
}

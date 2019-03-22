package com.navexplorer.indexer.communityfund.entity;

import com.navexplorer.indexer.communityfund.exception.ProposalStateNotFoundException;

public enum ProposalState {
    PENDING(0),
    ACCEPTED(1),
    REJECTED(2),
    EXPIRED(3),
    PENDING_FUNDS(4),
    PENDING_VOTING_PREQ(5);

    Integer id;

    ProposalState(Integer id) {
        this.id = id;
    }

    public Integer id() {
        return id;
    }

    public static ProposalState fromId(Integer id) {
        for (ProposalState proposalState : ProposalState.values()) {
            if (proposalState.id.equals(id)) {
                return proposalState;
            }
        }

        throw new ProposalStateNotFoundException(String.format("Proposal State %d not found", id));
    }
}

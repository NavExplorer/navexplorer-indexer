package com.navexplorer.indexer.communityfund.entity;

import com.navexplorer.indexer.communityfund.exception.PaymentRequestStateNotFoundException;

public enum PaymentRequestState {
    PENDING(0),
    ACCEPTED(1),
    REJECTED(2),
    EXPIRED(3);

    Integer id;

    PaymentRequestState(Integer id) {
        this.id = id;
    }

    public Integer id() {
        return id;
    }

    public static PaymentRequestState fromId(Integer id) {
        for (PaymentRequestState paymentRequestState : PaymentRequestState.values()) {
            if (paymentRequestState.id.equals(id)) {
                return paymentRequestState;
            }
        }

        throw new PaymentRequestStateNotFoundException(String.format("Payment Request State %d not found", id));
    }
}

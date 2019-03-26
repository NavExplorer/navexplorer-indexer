package com.navexplorer.indexer.navcoin.service;

import com.navexplorer.indexer.address.exception.AddressNotValidException;
import org.navcoin.NavcoinApi;
import org.navcoin.exception.NavcoinException;
import org.navcoin.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavcoinService {
    private static final Logger logger = LoggerFactory.getLogger(NavcoinService.class);

    @Autowired
    private NavcoinApi navcoinApi;

    public Info getInfo() {
        return navcoinApi.getInfo();
    }

    public String getBestBlockHash() {
        return navcoinApi.getBestBlockHash();
    }

    public Block getBlockByHeight(Long height) {
        Block block;
        try {
            return navcoinApi.getBlock(navcoinApi.getBlockHash(height.intValue()));
        } catch(NavcoinException e) {
            block = null;
        }

        return block;
    }

    public Block getBlockByHash(String hash) {
        Block block;
        try {
            block = navcoinApi.getBlock(hash);
        } catch(NavcoinException e) {
            logger.error(e.getMessage());
            block = null;
        }

        return block;

    }

    public Transaction getTransactionByHash(String hash) {
        return navcoinApi.getTransaction(hash);
    }

    public ValidateAddress getValidateAddressByHash(String hash) throws AddressNotValidException {
        return navcoinApi.validateAddress(hash);
    }

    public boolean isAddressValid(String hash) {
        return navcoinApi.validateAddress(hash).getIsvalid();
    }

    public Proposal getProposal(String hash) {
        return navcoinApi.getProposal(hash);
    }

    public List<Proposal> listProposals(String status) {
        return navcoinApi.listProposals(status);
    }

    public PaymentRequest getPaymentRequest(String hash) {
        return navcoinApi.getPaymentRequest(hash);
    }

    public CFundStats getCFundStats() {
        return navcoinApi.getCFundStats();
    }
}

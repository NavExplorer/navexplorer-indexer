package com.navexplorer.indexer.communityfund.factory;

import com.navexplorer.indexer.communityfund.exception.InvalidAnonDestinationException;
import com.navexplorer.indexer.communityfund.exception.InvalidVersionException;
import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.communityfund.entity.CommunityFundProposal;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunityFundProposalFactoryTest {
    @Test(expected = InvalidVersionException.class)
    public void it_requires_a_block_transaction_version_of_4() {
        BlockTransaction transaction = new BlockTransaction();
        transaction.setVersion(1);

        CommunityFundProposalFactory factory = new CommunityFundProposalFactory();
        factory.createProposal(transaction);
    }

    @Test(expected = InvalidAnonDestinationException.class)
    public void it_will_ignore_transactions_with_invalid_anon_destinations() {
        BlockTransaction transaction = new BlockTransaction();
        transaction.setVersion(4);
        transaction.setAnonDestination("yes;4020100");

        CommunityFundProposalFactory factory = new CommunityFundProposalFactory();
        factory.createProposal(transaction);
    }

    @Test(expected = InvalidAnonDestinationException.class)
    public void it_will_ignore_transactions_with_incomplete_anon_destinations() {
        BlockTransaction transaction = new BlockTransaction();
        transaction.setVersion(4);
        transaction.setAnonDestination("{}");

        CommunityFundProposalFactory factory = new CommunityFundProposalFactory();
        factory.createProposal(transaction);
    }

    @Test
    public void it_can_create_a_community_fund_proposal() {
        BlockTransaction transaction = new BlockTransaction();
        transaction.setVersion(4);
        transaction.setAnonDestination("{\"n\":44400000000,\"a\":\"ADDRESS\",\"d\":1533247200,\"s\":\"A simple proposal to begin the games\",\"v\":2}");
        transaction.setHeight(1000);

        CommunityFundProposalFactory factory = new CommunityFundProposalFactory();
        CommunityFundProposal proposal = factory.createProposal(transaction);

        assertThat(proposal.getHeight()).isEqualTo(transaction.getHeight().longValue());
        assertThat(proposal.getAmount()).isEqualTo(44400000000.0);
        assertThat(proposal.getAddress()).isEqualTo("ADDRESS");
        assertThat(proposal.getDeadline()).isEqualTo(new Date(1533247200 * 1000L));
        assertThat(proposal.getDescription()).isEqualTo("A simple proposal to begin the games");
    }
}

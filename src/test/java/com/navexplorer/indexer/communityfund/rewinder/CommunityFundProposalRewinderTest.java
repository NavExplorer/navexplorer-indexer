package com.navexplorer.indexer.communityfund.rewinder;

import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.communityfund.entity.CommunityFundProposal;
import com.navexplorer.library.communityfund.repository.CommunityFundProposalRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CommunityFundProposalRewinderTest {
    @InjectMocks
    private CommunityFundProposalRewinder rewinder;

    @Mock
    private CommunityFundProposalRepository repository;

    @Test
    public void it_can_rewind_proposals_by_transaction_height() {
        BlockTransaction transaction = new BlockTransaction();
        transaction.setHeight(10000);

        CommunityFundProposal proposal = new CommunityFundProposal();
        List<CommunityFundProposal> proposals = Arrays.asList(proposal, proposal);

        when(repository.findAllByHeight(transaction.getHeight().longValue())).thenReturn(proposals);

        rewinder.rewindProposal(transaction);

        verify(repository, times(2)).delete(proposal);
    }
}

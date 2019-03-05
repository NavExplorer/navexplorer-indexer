package com.navexplorer.indexer.communityfund.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Document(collection = "communityFundProposalVote")
public class ProposalVote {
    @NotNull
    @Id
    private String id;

    @Indexed
    private int height;

    private String address;
    private String proposal;
    private Boolean vote;
}

package com.navexplorer.indexer.communityfund.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Document(collection = "communityFundStats")
public class CommunityFundStats {
    @NotNull
    @Id
    private String id;

    private Double available;
    private Double locked;
    private Double spent;
}

package com.navexplorer.indexer.communityfund.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Component
@ConfigurationProperties(prefix = "communityfund")
@Data
public class CommunityFundProperties {
    @NotNull
    private Integer blocksInCycle;

    @NotNull
    private Double minQuorum;

    @NotNull
    private VotingProperties proposalVoting;

    @NotNull
    private VotingProperties paymentVoting;

    @Data
    public static class VotingProperties {
        @NotNull
        private Integer cycles;

        @NotNull
        private Double accept;

        @NotNull
        private Double reject;
    }
}
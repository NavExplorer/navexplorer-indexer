package com.navexplorer.indexer.communityfund.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StrDZeel {
    @NotNull
    @JsonProperty("n")
    private Double amount;

    @NotNull
    @JsonProperty("a")
    private String address;

    @NotNull
    @JsonProperty("d")
    private Integer deadline;

    @NotNull
    @JsonProperty("s")
    private String description;
}

package com.navexplorer.indexer.block.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Document(collection = "blockTransaction")
public class BlockTransaction {
    private static final Logger logger = LoggerFactory.getLogger(BlockTransaction.class);

    @NotNull
    @Id
    String id;

    @NotNull
    @Indexed(unique=true)
    String hash;

    @NotNull
    @Indexed
    Integer height;

    @NotNull
    Date time;

    Double stake = 0.0;

    Double fees = 0.0;

    @NotNull
    @JsonIgnore
    String blockHash;

    List<Input> inputs = new ArrayList<>();

    List<Output> outputs = new ArrayList<>();

    BlockTransactionType type;

    Integer version;

    String anonDestination;

    @Transient
    String raw;

    @JsonIgnore
    public Double getOutputAmount() {
        return outputs.stream().mapToDouble(Output::getAmount).sum();
    }

    @JsonIgnore
    public Double getInputAmount() {
        return inputs.stream().mapToDouble(Input::getAmount).sum();
    }

    public boolean hasInputWithAddress(String address) {
        return inputs.stream().anyMatch(i -> i.getAddresses().contains(address));
    }

    public List<Input> getInputsByAddress(String address) {
        return inputs.stream().filter(i -> i.getAddresses().contains(address)).collect(Collectors.toList());
    }

    public Output getOutput(Integer index) {
        Optional<Output> output = outputs.stream().filter(o -> o.getIndex().equals(index)).findFirst();

        return output.orElse(null);
    }

    public List<Output> getOutputsByAddress(String address) {
        return outputs.stream().filter(i -> i.getAddresses().contains(address)).collect(Collectors.toList());
    }

    public boolean hasOutputOfType(OutputType outputType) {
        return outputs.stream().filter(o -> o.getType() != null).anyMatch(o -> o.getType().equals(outputType));
    }

    @JsonIgnore
    public boolean isCoinbase() {
        return type == BlockTransactionType.COINBASE || type == BlockTransactionType.EMPTY;
    }

    @JsonIgnore
    public boolean isStaking() {
        return type == BlockTransactionType.STAKING;
    }

    @JsonIgnore
    public boolean isSpend() {
        return type == BlockTransactionType.SPEND;
    }

    @JsonIgnore
    public boolean isPrivateSpend() {
        return type == BlockTransactionType.PRIVATE_SPEND;
    }

    @JsonIgnore
    public boolean isColdStaking() {
        return type == BlockTransactionType.COLD_STAKING;
    }

    @JsonIgnore
    public boolean isPrivateStaking() {
        return type == BlockTransactionType.PRIVATE_STAKING;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public Map<String, Object> getAnonDestinationObject() {
        Map<String, Object> map = new HashMap<>();

        try {
            if (anonDestination != null) {
                ObjectMapper mapper = new ObjectMapper();
                map = mapper.readValue(anonDestination, new TypeReference<Map<String, String>>() {
                });
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return map;
    }
}

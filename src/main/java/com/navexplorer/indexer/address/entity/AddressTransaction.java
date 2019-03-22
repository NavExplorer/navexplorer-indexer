package com.navexplorer.indexer.address.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "addressTransaction")
public class AddressTransaction {
    @NotNull
    @Id
    String id;

    Date time;
    String address;
    AddressTransactionType type;
    String transaction;
    Integer height;
    Boolean standard = false;
    Double balance = 0.0;
    Double sent = 0.0;
    Double received = 0.0;
    Boolean coldStaking = false;
    Double coldStakingBalance = 0.0;
    Double coldStakingSent = 0.0;
    Double coldStakingReceived = 0.0;
    Double delegateStakingSent = 0.0;
    Double delegateStakingReceived = 0.0;
}

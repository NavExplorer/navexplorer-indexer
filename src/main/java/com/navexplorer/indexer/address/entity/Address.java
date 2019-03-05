package com.navexplorer.indexer.address.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Document(collection = "address")
public class Address {
    @NotNull
    @Id
    @JsonIgnore
    String id;

    @Indexed(unique=true)
    String hash;

    Double received = 0.0;
    Integer receivedCount = 0;

    Double sent = 0.0;
    Integer sentCount = 0;

    Double staked = 0.0;
    Integer stakedCount = 0;
    Double stakedSent = 0.0;
    Double stakedReceived = 0.0;

    Double coldStaked = 0.0;
    Integer coldStakedCount = 0;
    Double coldStakedSent = 0.0;
    Double coldStakedReceived = 0.0;

    Double coldStakedBalance = 0.0;
    Double balance = 0.0;

    Integer blockIndex = 0;

    @Transient
    Long richListPosition;

    String label;

    public Address(String hash) {
        this.hash = hash;
    }

    public void receive(Double amount) {
        received += amount;
        receivedCount ++;
        balance += amount;
    }

    public void send(Double amount) {
        sent += amount;
        sentCount ++;
        balance -= amount;
    }

    public void stake(Double sent, Double received) {
        Double amount = received - sent;
        stakedSent += sent;
        stakedReceived += received;
        staked += amount;
        stakedCount ++;
        balance += amount;
    }

    public void coldStake(Double sent, Double received) {
        Double amount = received - sent;
        coldStakedSent += sent;
        coldStakedReceived += received;
        coldStaked += amount;
        coldStakedCount ++;
        coldStakedBalance += amount;
    }
}

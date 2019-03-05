package com.navexplorer.indexer.configuration.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Document(collection = "configuration")
public class Configuration {
    @NotNull
    @Id
    @JsonIgnore
    String id;
    String name;
    Object value;
}

package com.navexplorer.indexer.configuration.repository;

import com.navexplorer.indexer.configuration.entity.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableMongoRepositories()
public interface ConfigurationRepository extends MongoRepository<Configuration, String> {
    Configuration findFirstByName(String name);
}

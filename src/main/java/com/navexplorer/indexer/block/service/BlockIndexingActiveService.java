package com.navexplorer.indexer.block.service;

import com.navexplorer.indexer.configuration.entity.Configuration;
import com.navexplorer.indexer.configuration.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockIndexingActiveService {
    @Autowired
    ConfigurationRepository configurationRepository;

    public Boolean isActive() {
        Configuration configuration = configurationRepository.findFirstByName("blockIndexActive");

        if (configuration == null) {
            configuration = new Configuration();
            configuration.setName("blockIndexActive");
            configuration.setValue(true);
            configurationRepository.save(configuration);
        }

        return (Boolean) configuration.getValue();
    }
}

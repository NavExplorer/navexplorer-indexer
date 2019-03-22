package com.navexplorer.indexer.configuration.service;

import com.navexplorer.indexer.configuration.entity.Configuration;
import com.navexplorer.indexer.configuration.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {
    @Autowired
    private ConfigurationRepository configurationRepository;

    public void save(Configuration configuration) {
        configurationRepository.save(configuration);
    }
}

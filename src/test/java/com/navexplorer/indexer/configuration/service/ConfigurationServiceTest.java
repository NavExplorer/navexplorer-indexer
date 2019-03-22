package com.navexplorer.indexer.configuration.service;

import com.navexplorer.indexer.configuration.entity.Configuration;
import com.navexplorer.indexer.configuration.repository.ConfigurationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationServiceTest {
    @InjectMocks
    private ConfigurationService configurationService;

    @Mock
    ConfigurationRepository configurationRepository;

    @Test
    public void it_can_save_a_configuration() {
        Configuration config = new Configuration();

        configurationService.save(config);

        verify(configurationRepository).save(config);
    }
}

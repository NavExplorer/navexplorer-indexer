package com.navexplorer.indexer;

import org.navcoin.Client;
import org.navcoin.NavcoinApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableMongoRepositories
@PropertySource(value = "classpath:application.yaml")
public class IndexerConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Value("${navcoin.properties.host}")
    private String navcoinHost;

    @Value("${navcoin.properties.port}")
    private Integer navcoinPort;

    @Value("${navcoin.properties.protocol}")
    private String navcoinProtocol;

    @Value("${navcoin.properties.username}")
    private String navcoinUsername;

    @Value("${navcoin.properties.password}")
    private String navcoinPassword;

    @Bean
    NavcoinApi navcoinApi() {
        Client client = new Client(navcoinHost, navcoinPort, navcoinProtocol, navcoinUsername, navcoinPassword);

        return new NavcoinApi(client);
    }
}
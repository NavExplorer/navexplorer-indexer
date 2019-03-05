package com.navexplorer.indexer.configuration.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationEntityTest {
    @Test
    public void it_can_set_and_get_id() {
        String id = "CONFIG_ID";

        Configuration config = new Configuration();
        config.setId(id);

        assertThat(config.getId()).isEqualTo(id);
    }

    @Test
    public void it_can_set_and_get_name() {
        String name = "CONFIG_NAME";

        Configuration config = new Configuration();
        config.setName(name);

        assertThat(config.getName()).isEqualTo(name);
    }

    @Test
    public void it_can_set_and_get_value() {
        Object value = new Object();

        Configuration config = new Configuration();
        config.setValue(value);

        assertThat(config.getValue()).isEqualTo(value);
    }
}

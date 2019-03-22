package com.navexplorer.indexer.softfork;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navexplorer.indexer.softfork.entity.SoftFork;
import com.navexplorer.indexer.softfork.entity.SoftForkState;
import com.navexplorer.indexer.softfork.repository.SoftForkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class SoftForkImporter {
    private static final Logger logger = LoggerFactory.getLogger(SoftForkImporter.class);

    @Autowired
    private SoftForkRepository softForkRepository;

    public void importSoftForks() {
        File signalsFile = new File("./config/softForks.json");

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<SoftFork> softForks = mapper.readValue(signalsFile, new TypeReference<List<SoftFork>>(){});

            softForks.forEach(s -> {
                s.setState(SoftForkState.DEFINED);
                try {
                    softForkRepository.save(s);
                } catch (Exception e) {
                    // only save if new
                }
            });
        } catch (IOException e) {
            logger.error("Could not read " + signalsFile.getAbsoluteFile());
        }
    }
}

package com.poptsov.marketplace.integration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@TestConfiguration
@Slf4j
@ActiveProfiles("test")

public class MarketplaceApplicationTests {


    @PostConstruct
    public void init() {
      log.info("MarketplaceApplicationTests is initialized");
    }
}

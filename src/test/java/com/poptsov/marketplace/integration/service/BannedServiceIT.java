package com.poptsov.marketplace.integration.service;

import com.poptsov.marketplace.annotations.IT;
import com.poptsov.marketplace.service.BannedService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

@IT
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BannedServiceIT {

   private final BannedService service;

    @Test
    void test() {

    }
}

package com.poptsov.marketplace.integration.service;

import com.poptsov.marketplace.annotations.IT;
import com.poptsov.marketplace.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

@IT
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class AuthenticationServiceIT {

    private final AuthenticationService authenticationService;

    @Test
    void test() {

    }
}

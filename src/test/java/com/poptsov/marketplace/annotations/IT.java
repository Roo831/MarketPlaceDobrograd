package com.poptsov.marketplace.annotations;

import com.poptsov.marketplace.integration.MarketplaceApplicationTests;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@SpringBootTest(classes = {MarketplaceApplicationTests.class})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public @interface IT {
}

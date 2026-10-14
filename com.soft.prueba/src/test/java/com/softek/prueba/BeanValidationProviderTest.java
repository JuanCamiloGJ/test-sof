package com.softek.prueba;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

/**
 * Since Spring Boot 3.0, spring-boot-starter-web no longer ships a Bean
 * Validation implementation. The API still reaches the classpath through
 * hibernate-core, so without a provider Hibernate logs
 * NoProviderFoundException at startup and every constraint annotation
 * (@NotNull, @Size, @Valid, ...) is silently ignored.
 */
class BeanValidationProviderTest {

    @Test
    void aBeanValidationProviderIsOnTheClasspath() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            assertThat(factory.getValidator()).isNotNull();
        }
    }
}

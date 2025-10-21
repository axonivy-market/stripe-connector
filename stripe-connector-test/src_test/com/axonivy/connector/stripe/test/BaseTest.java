package com.axonivy.connector.stripe.test;

import org.junit.jupiter.api.BeforeEach;

import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;

@IvyProcessTest
public class BaseTest {

    @BeforeEach
    void setup(AppFixture fixture) {
        // Set up Stripe API keys for testing
        // Use test keys that are safe for unit testing
        fixture.var("stripe.auth.secretKey", "sk_test_mock_secret_key_for_testing");
        fixture.var("stripe.auth.publishableKey", "pk_test_mock_publishable_key_for_testing");
    }
}

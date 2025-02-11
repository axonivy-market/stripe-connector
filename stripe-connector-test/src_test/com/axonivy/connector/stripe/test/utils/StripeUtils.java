package com.axonivy.connector.stripe.test.utils;

import ch.ivyteam.ivy.environment.AppFixture;

public class StripeUtils {

  public static void setUpConfigForContext(AppFixture fixture) {
    setUpConfigForApiTest(fixture);
  }

  public static void setUpConfigForApiTest(AppFixture fixture) {
    String secretKey = System.getProperty("secretKey");
    String publishableKey = System.getProperty("publishableKey");
    fixture.var("stripe.auth.secretKey", secretKey);
    fixture.var("stripe.auth.publishableKey", publishableKey);
  }
}

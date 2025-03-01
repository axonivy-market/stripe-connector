package com.axonivy.connector.stripe.test.utils;

import com.axonivy.ivy.webtest.engine.WebAppFixture;

public class StripeUtils {

  public static void setUpConfigForApiTest(WebAppFixture fixture) {
    String secretKey = System.getProperty("secretKey");
    String publishableKey = System.getProperty("publishableKey");
    fixture.var("stripe.auth.secretKey", secretKey);
    fixture.var("stripe.auth.publishableKey", publishableKey);
  }
}

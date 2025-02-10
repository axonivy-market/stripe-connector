package com.axonivy.connector.stripe.test.utils;

import ch.ivyteam.ivy.environment.AppFixture;

public class StripeUtils {

  public static void setUpConfigForContext(AppFixture fixture) {
    setUpConfigForApiTest(fixture);
  }

  public static void setUpConfigForApiTest(AppFixture fixture) {
    String secretKey = System.getProperty("secretKey");
    String publishableKey = System.getProperty("publishableKey");
//    fixture.var("stripe.auth.secretKey", secretKey);
//    fixture.var("stripe.auth.publishableKey", publishableKey);
    
    fixture.var("stripe.auth.secretKey", "sk_test_51Qe9v3LaeAomYD3LDQoSSPIlT5XaWCDP19UYwOnUw7ODspcVu7C2WzAQq9E1PiEXn6GagMNMaEd1My2E4yNq3MGA005gofIbbz");
    fixture.var("stripe.auth.publishableKey", "pk_test_51Qe9v3LaeAomYD3LNDn8V1WzZSn6HSx8AHVeKxq8ZGee5Q8ONKDGsrL6hXLFj33gvzl080xMcdhQXMS4fDzipJeX00Fe0BdnpK");
  }
}

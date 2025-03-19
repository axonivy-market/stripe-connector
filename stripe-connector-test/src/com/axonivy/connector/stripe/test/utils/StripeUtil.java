package com.axonivy.connector.stripe.test.utils;

public class StripeUtil {

	public static String getSecret() {
		String a = System.getProperty("secretKey");
		System.out.println("#getSecret----- " + a);
		return "sk_test_51Qe9v3LaeAomYD3LDQoSSPIlT5XaWCDP19UYwOnUw7ODspcVu7C2WzAQq9E1PiEXn6GagMNMaEd1My2E4yNq3MGA005gofIbbz";
	}
	
	public static String getPublic() {
		String b = System.getProperty("publishableKey");
		System.out.println("#getPublic----- " + b);
		return "pk_test_51Qe9v3LaeAomYD3LNDn8V1WzZSn6HSx8AHVeKxq8ZGee5Q8ONKDGsrL6hXLFj33gvzl080xMcdhQXMS4fDzipJeX00Fe0BdnpK";
	}
}

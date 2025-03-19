package com.axonivy.connector.stripe.test.utils;

public class StripeUtil {

	public static String getSecret() {
		System.out.println("#getSecret --- " + System.getProperty("secretKey"));
		return System.getProperty("secretKey");
	}
	
	public static String getPublic() {
		System.out.println("#getPublic --- " + System.getProperty("publishableKey"));
		return System.getProperty("publishableKey");
	}
}

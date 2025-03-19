package com.axonivy.connector.stripe.test.utils;

public class StripeUtil {

	public static String getSecret() {
		return System.getProperty("secretKey");
	}
	
	public static String getPublic() {
		return System.getProperty("publishableKey");
	}
}

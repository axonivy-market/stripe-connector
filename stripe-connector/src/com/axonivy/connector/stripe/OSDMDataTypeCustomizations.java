package com.axonivy.connector.stripe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.stripe.api.client.PaymentLink;


public class OSDMDataTypeCustomizations extends SimpleModule {

	private static final long serialVersionUID = 7937918079183158890L;

	public OSDMDataTypeCustomizations(ObjectMapper mapper) {
		addDeserializer(PaymentLink.class, new PaymentLinkDeserializer(mapper));
	}
}

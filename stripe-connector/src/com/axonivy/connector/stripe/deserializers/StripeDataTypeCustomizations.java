package com.axonivy.connector.stripe.deserializers;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.stripe.api.client.CheckoutSession;
import com.stripe.api.client.PaymentLink;


public class StripeDataTypeCustomizations extends SimpleModule {

  private static final long serialVersionUID = 7937918079183158890L;

  public StripeDataTypeCustomizations() {
    addDeserializer(PaymentLink.class, new PaymentLinkDeserializer());
    addDeserializer(CheckoutSession.class, new EmbededCheckoutSessionDeserializer());
  }
}

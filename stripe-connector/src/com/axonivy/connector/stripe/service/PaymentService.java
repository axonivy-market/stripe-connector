package com.axonivy.connector.stripe.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.param.checkout.SessionCreateParams;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.call.SubProcessCall;
import ch.ivyteam.ivy.process.call.SubProcessCallResult;

public class PaymentService {
  private static final String CLIENT_SECRET = "clientSecret";
  private static final String URL = "url";
  private static final String PRICE_ID = "priceId";
  private static final String QUANTITY = "quantity";
  private static final String PAYMENT_LINK_ID = "paymentLinkId";
  private static final String ACTIVE = "active";
  private static final PaymentService instance = new PaymentService();

  public PaymentService() {
    Stripe.apiKey = Ivy.var().get("stripe.auth.secretKey");
  }

  public static PaymentService getInstance() {
    return instance;
  }

  public String getClientSecret(String priceId, Long quantity) {
    SubProcessCallResult callResult = SubProcessCall.withPath("embededCheckoutSession")
        .withStartName("embededCheckoutSession").withParam(PRICE_ID, priceId).withParam(QUANTITY, quantity).call();

    return callResult != null ? callResult.get(CLIENT_SECRET).toString() : null;
  }

  public String getPaymentLink(String priceId, Long quantity) {
    SubProcessCallResult callResult = SubProcessCall.withPath("paymentLink").withStartName("paymentLink")
        .withParam(PRICE_ID, priceId).withParam(QUANTITY, quantity).call();

    return callResult != null ? callResult.get(URL).toString() : null;
  }

  public JsonNode createPaymentLink(String priceId, Long quantity) {
    SubProcessCallResult callResult = SubProcessCall.withPath("paymentLink").withStartName("createPaymentLink")
        .withParam(PRICE_ID, priceId).withParam(QUANTITY, quantity).call();
    return callResult != null ? callResult.first(JsonNode.class) : null;
  }

  public JsonNode retrievePaymentLink(String paymentLinkId) {
    SubProcessCallResult callResult = SubProcessCall.withPath("paymentLink").withStartName("retrievePaymentLink")
        .withParam(PAYMENT_LINK_ID, paymentLinkId).call();
    return callResult != null ? callResult.first(JsonNode.class) : null;
  }

  public JsonNode retrievePaymentLinkLineItems(String paymentLinkId) {
    SubProcessCallResult callResult = SubProcessCall.withPath("paymentLink")
        .withStartName("retrievePaymentLinkLineItems").withParam(PAYMENT_LINK_ID, paymentLinkId).call();
    return callResult != null ? callResult.first(JsonNode.class) : null;
  }

  public JsonNode deactivatePaymentLink(String paymentLinkId) {
    return setPaymentLinkActive(paymentLinkId, false);
  }

  public JsonNode setPaymentLinkActive(String paymentLinkId, boolean active) {
    SubProcessCallResult callResult = SubProcessCall.withPath("paymentLink").withStartName("setPaymentLinkActive")
        .withParam(PAYMENT_LINK_ID, paymentLinkId).withParam(ACTIVE, active).call();
    return callResult != null ? callResult.first(JsonNode.class) : null;
  }

  public Price retrievePrice(String priceId) {
    try {
      return Price.retrieve(priceId);
    } catch (StripeException exception) {
      Ivy.log().error(exception.getUserMessage());
      return null;
    }
  }

  public SessionCreateParams.Mode getPaymentMode(Price price) {
    return Optional.of(price).map(Price::getRecurring).map(r -> SessionCreateParams.Mode.SUBSCRIPTION)
        .orElse(SessionCreateParams.Mode.PAYMENT);
  }
}

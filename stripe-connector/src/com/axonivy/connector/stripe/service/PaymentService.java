package com.axonivy.connector.stripe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.RedirectOnCompletion;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.call.SubProcessCall;
import ch.ivyteam.ivy.process.call.SubProcessCallResult;

import static com.stripe.param.checkout.SessionCreateParams.PaymentMethodType.CARD;
import static com.stripe.param.checkout.SessionCreateParams.PaymentMethodType.SEPA_DEBIT;

public class PaymentService {
  private static final String EURO_CURRENCY = "eur";
  private static final PaymentService instance = new PaymentService();
  private static final List<SessionCreateParams.PaymentMethodType> SUPPORTED_PAYMENT_METHOD_TYPES =
      new ArrayList<>(List.of(CARD));

  public PaymentService() {
    Stripe.apiKey = Ivy.var().get("stripe.auth.secretKey");
  }

  public static PaymentService getInstance() {
    return instance;
  }


  public void test() {
    SubProcessCallResult callResult =
        SubProcessCall.withPath("embededCheckoutSession").withStartName("embededCheckoutSession")
            .withParam("priceId", "price_1QeXzELaeAomYD3LHr9WqImT").withParam("quantity", 2).call();
    if (callResult != null) {
      Ivy.log().warn(callResult.get("clientSecret"));
    }
  }

  public String getClientSecretViaOpenApi(String priceId, Long quantity) {
    SubProcessCallResult callResult = SubProcessCall.withPath("embededCheckoutSession")
        .withStartName("embededCheckoutSession").withParam("priceId", priceId).withParam("quantity", quantity).call();

    return callResult != null ? callResult.get("clientSecret").toString() : null;
  }

  public String getPaymentLinkViaOpenApi(String priceId, Long quantity) {
    SubProcessCallResult callResult = SubProcessCall.withPath("paymentLink").withStartName("paymentLink")
        .withParam("priceId", priceId).withParam("quantity", quantity).call();

    return callResult != null ? callResult.get("url").toString() : null;
  }


  public PaymentLink createPaymentLink(String priceId, Long quantity) throws StripeException {
    PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
        .addLineItem(PaymentLinkCreateParams.LineItem.builder().setPrice(priceId).setQuantity(quantity).build())
        .build();
    return PaymentLink.create(params);
  }

  public Session createCheckoutSession(String priceId, Long quantity) throws StripeException {
    Price price = this.retrievePrice(priceId);

    SessionCreateParams params = SessionCreateParams.builder().setMode(getPaymentMode(price))
        .setUiMode(SessionCreateParams.UiMode.EMBEDDED).setRedirectOnCompletion(RedirectOnCompletion.NEVER)
        .addAllPaymentMethodType(getSupportedPaymentMethodTypes(price))
        .addLineItem(SessionCreateParams.LineItem.builder().setPrice(priceId).setQuantity(quantity).build()).build();

    return Session.create(params);
  }

  public List<SessionCreateParams.PaymentMethodType> getSupportedPaymentMethodTypes(Price price) {
    if (EURO_CURRENCY.equals(price.getCurrency())) {
      SUPPORTED_PAYMENT_METHOD_TYPES.add(SEPA_DEBIT);
    }
    return SUPPORTED_PAYMENT_METHOD_TYPES;
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

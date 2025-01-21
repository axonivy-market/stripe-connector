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

  public PaymentLink createPaymentLink(String priceId, Long quantity) throws StripeException {
    PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
        .addLineItem(PaymentLinkCreateParams.LineItem.builder().setPrice(priceId).setQuantity(quantity).build())
        .build();
    return PaymentLink.create(params);
  }

  public Session createCheckoutSession(String priceId, Long quantity) throws StripeException {
    Price price = Price.retrieve(priceId);

    SessionCreateParams.Mode paymentMode = Optional.of(price).map(Price::getRecurring)
        .map(r -> SessionCreateParams.Mode.SUBSCRIPTION).orElse(SessionCreateParams.Mode.PAYMENT);

    SessionCreateParams params = SessionCreateParams.builder().setMode(paymentMode)
        .setUiMode(SessionCreateParams.UiMode.EMBEDDED).setRedirectOnCompletion(RedirectOnCompletion.NEVER)
        .addAllPaymentMethodType(getSupportedPaymentMethodTypes(price))
        .addLineItem(SessionCreateParams.LineItem.builder().setPrice(priceId).setQuantity(quantity).build()).build();

    return Session.create(params);
  }

  private List<SessionCreateParams.PaymentMethodType> getSupportedPaymentMethodTypes(Price price) {
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
}

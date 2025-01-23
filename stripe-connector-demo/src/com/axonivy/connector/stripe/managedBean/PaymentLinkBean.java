package com.axonivy.connector.stripe.managedBean;

import com.axonivy.connector.stripe.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PaymentLinkBean {
  private String priceId;
  private long quantity;
  private String result;
  private boolean disableForm;

  public void onSendRequest() throws StripeException {
    PaymentLink paymentLink = PaymentService.getInstance().createPaymentLink(priceId, quantity);
    result = paymentLink.getUrl();
    this.disableForm = true;
  }

  public String getPriceId() {
    return priceId;
  }

  public void setPriceId(String priceId) {
    this.priceId = priceId;
  }

  public long getQuantity() {
    return quantity;
  }

  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public boolean isDisableForm() {
    return disableForm;
  }

  public void setDisableForm(boolean disableForm) {
    this.disableForm = disableForm;
  }

}

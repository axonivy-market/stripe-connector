package com.axonivy.connector.stripe.managedBean;

import com.axonivy.connector.stripe.service.PaymentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.stripe.exception.StripeException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PaymentLinkBean {
  private String priceId;
  private long quantity;
  private String result;
  private boolean disableForm;
  private String paymentLinkId;
  private boolean active;
  private String createResult;
  private String retrieveResult;
  private String retrieveLineItemsResult;
  private String setActiveResult;

  public void onSendRequest() throws StripeException {
    result = PaymentService.getInstance().getPaymentLink(priceId, quantity);
    this.disableForm = true;
  }

  public void onCreatePaymentLink() throws StripeException {
    JsonNode jsonResult = PaymentService.getInstance().createPaymentLink(priceId, quantity);
    createResult = jsonResult != null ? jsonResult.toPrettyString() : "No result";
  }

  public void onRetrievePaymentLink() throws StripeException {
    JsonNode jsonResult = PaymentService.getInstance().retrievePaymentLink(paymentLinkId);
    retrieveResult = jsonResult != null ? jsonResult.toPrettyString() : "No result";
  }

  public void onRetrievePaymentLinkLineItems() throws StripeException {
    JsonNode jsonResult = PaymentService.getInstance().retrievePaymentLinkLineItems(paymentLinkId);
    retrieveLineItemsResult = jsonResult != null ? jsonResult.toPrettyString() : "No result";
  }

  public void onSetPaymentLinkActive() throws StripeException {
    JsonNode jsonResult = PaymentService.getInstance().setPaymentLinkActive(paymentLinkId, active);
    setActiveResult = jsonResult != null ? jsonResult.toPrettyString() : "No result";
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

  // Getters and setters for new fields
  public String getPaymentLinkId() {
    return paymentLinkId;
  }

  public void setPaymentLinkId(String paymentLinkId) {
    this.paymentLinkId = paymentLinkId;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getCreateResult() {
    return createResult;
  }

  public void setCreateResult(String createResult) {
    this.createResult = createResult;
  }

  public String getRetrieveResult() {
    return retrieveResult;
  }

  public void setRetrieveResult(String retrieveResult) {
    this.retrieveResult = retrieveResult;
  }

  public String getRetrieveLineItemsResult() {
    return retrieveLineItemsResult;
  }

  public void setRetrieveLineItemsResult(String retrieveLineItemsResult) {
    this.retrieveLineItemsResult = retrieveLineItemsResult;
  }

  public String getSetActiveResult() {
    return setActiveResult;
  }

  public void setSetActiveResult(String setActiveResult) {
    this.setActiveResult = setActiveResult;
  }

}

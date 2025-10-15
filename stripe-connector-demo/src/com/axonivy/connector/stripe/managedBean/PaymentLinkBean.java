package com.axonivy.connector.stripe.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.axonivy.connector.stripe.service.PaymentService;
import com.stripe.api.client.PaymentLink;
import com.stripe.api.client.PaymentLinksResourceListLineItems;
import com.stripe.exception.StripeException;

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
    PaymentLink paymentLinkResult = PaymentService.getInstance().createPaymentLink(priceId, quantity);
    createResult = paymentLinkResult != null ? formatPaymentLinkResult(paymentLinkResult) : "No result";
  }

  public void onRetrievePaymentLink() throws StripeException {
    PaymentLink paymentLinkResult = PaymentService.getInstance().retrievePaymentLink(paymentLinkId);
    retrieveResult = paymentLinkResult != null ? formatPaymentLinkResult(paymentLinkResult) : "No result";
  }

  public void onRetrievePaymentLinkLineItems() throws StripeException {
    PaymentLinksResourceListLineItems lineItemsResult = PaymentService.getInstance().retrievePaymentLinkLineItems(paymentLinkId);
    retrieveLineItemsResult = lineItemsResult != null ? formatLineItemsResult(lineItemsResult) : "No result";
  }

  public void onSetPaymentLinkActive() throws StripeException {
    PaymentLink paymentLinkResult = PaymentService.getInstance().setPaymentLinkActive(paymentLinkId, active);
    setActiveResult = paymentLinkResult != null ? formatPaymentLinkResult(paymentLinkResult) : "No result";
  }

  private String formatPaymentLinkResult(PaymentLink paymentLink) {
    StringBuilder sb = new StringBuilder();
    sb.append("Payment Link Details:\n");
    sb.append("ID: ").append(paymentLink.getId()).append("\n");
    sb.append("URL: ").append(paymentLink.getUrl()).append("\n");
    sb.append("Active: ").append(paymentLink.isActive()).append("\n");
    return sb.toString();
  }

  private String formatLineItemsResult(PaymentLinksResourceListLineItems lineItems) {
    StringBuilder sb = new StringBuilder();
    sb.append("Line Items:\n");
    if (lineItems.getData() != null && !lineItems.getData().isEmpty()) {
      sb.append("Items Count: ").append(lineItems.getData().size()).append("\n");
      for (int i = 0; i < lineItems.getData().size(); i++) {
        var item = lineItems.getData().get(i);
        sb.append("Item ").append(i + 1).append(":\n");
        sb.append("  ID: ").append(item.getId()).append("\n");
        sb.append("  Amount Total: ").append(item.getAmountTotal()).append("\n");
        sb.append("  Amount Subtotal: ").append(item.getAmountSubtotal()).append("\n");
        sb.append("  Amount Tax: ").append(item.getAmountTax()).append("\n");
        sb.append("  Amount Discount: ").append(item.getAmountDiscount()).append("\n");
        sb.append("  Currency: ").append(item.getCurrency()).append("\n");
        sb.append("  Description: ").append(item.getDescription()).append("\n");
        sb.append("  Quantity: ").append(item.getQuantity()).append("\n");
      }
    }
    return sb.toString();
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

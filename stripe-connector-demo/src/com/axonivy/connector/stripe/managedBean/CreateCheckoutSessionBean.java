package com.axonivy.connector.stripe.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class CreateCheckoutSessionBean {
  private String priceId;
  private long quantity;
  private boolean disableForm;

  public void onRequest() {
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

  public boolean isDisableForm() {
    return disableForm;
  }

  public void setDisableForm(boolean disableForm) {
    this.disableForm = disableForm;
  }

}

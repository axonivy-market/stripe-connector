package com.axonivy.connector.stripe.managedBean;

import java.io.Serializable;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;


@Named
@ViewScoped
public class CreateCheckoutSessionBean implements Serializable {
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

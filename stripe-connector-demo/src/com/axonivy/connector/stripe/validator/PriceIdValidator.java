package com.axonivy.connector.stripe.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.stripe.service.PaymentService;
import com.stripe.model.Price;

@FacesValidator(value = "priceIdValidator")
public class PriceIdValidator implements Validator {

  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    if (isRequiredTextInputValidator(value)) {
      addGrowlErrorMessage("This value is required");
    }

    if (isInvalidPrice(value.toString())) {
      addGrowlErrorMessage("The priceId is invalid");
    }
  }

  private void addGrowlErrorMessage(String message) {
    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
  }

  private boolean isRequiredTextInputValidator(Object value) {
    return value == null || StringUtils.isBlank(value.toString());
  }

  private boolean isInvalidPrice(String priceId) {
    Price price = PaymentService.getInstance().retrievePrice(priceId);
    return ObjectUtils.isEmpty(price);
  }

}

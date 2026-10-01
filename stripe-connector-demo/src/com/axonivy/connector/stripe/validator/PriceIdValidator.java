package com.axonivy.connector.stripe.validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.stripe.service.PaymentService;
import com.stripe.model.Price;
import jakarta.enterprise.context.ApplicationScoped;

@FacesValidator(value = "priceIdValidator", managed = true)
@ApplicationScoped
public class PriceIdValidator implements Validator<Object> {

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

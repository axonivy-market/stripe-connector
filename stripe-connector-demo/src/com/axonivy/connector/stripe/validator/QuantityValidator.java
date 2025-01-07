package com.axonivy.connector.stripe.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang3.ObjectUtils;

@FacesValidator(value = "quantityValidator")
public class QuantityValidator implements Validator {
  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    if (ObjectUtils.isEmpty(value)) {
      addGrowlErrorMessage("This value is required");
    }

    if (Long.parseLong(value.toString()) < 1) {
      addGrowlErrorMessage("The quantity should be greater or equal to 1");
    }
  }

  private void addGrowlErrorMessage(String message) {
    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
  }

}

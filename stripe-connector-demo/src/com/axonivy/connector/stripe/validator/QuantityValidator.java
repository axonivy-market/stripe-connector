package com.axonivy.connector.stripe.validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import org.apache.commons.lang3.ObjectUtils;
import jakarta.enterprise.context.ApplicationScoped;

@FacesValidator(value = "quantityValidator", managed = true)
@ApplicationScoped
public class QuantityValidator implements Validator<Object> {
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

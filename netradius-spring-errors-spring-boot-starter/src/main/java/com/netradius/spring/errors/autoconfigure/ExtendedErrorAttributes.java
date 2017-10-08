package com.netradius.spring.errors.autoconfigure;

import com.netradius.spring.errors.context.SpringErrorsMessageSource;
import com.netradius.spring.errors.exception.ApiException;
import com.netradius.spring.errors.exception.ValidationFailedException;
import com.netradius.spring.errors.web.ValidationError;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Extends the DefaultErrorAttributes class in Spring Boot.
 *
 * @author Erik R. Jensen
 */
public class ExtendedErrorAttributes extends DefaultErrorAttributes {

  private final ExtendedErrorProperties extendedErrorProperties;
  private final SpringErrorsMessageSource springErrorsMessageSource;
  private final MessageSource messageSource;

  // TODO When Spring Boot 2.0 is released, they have a boolean to include exception
  public ExtendedErrorAttributes(ExtendedErrorProperties extendedErrorProperties,
      SpringErrorsMessageSource apiExceptionMessageSource,
      MessageSource messageSource) {
    this.extendedErrorProperties = extendedErrorProperties;
    this.springErrorsMessageSource = apiExceptionMessageSource;
    this.messageSource = messageSource;
  }

  protected String resolveMessage(MessageSourceResolvable resolvableMessage) {
    if (resolvableMessage != null) {
      try {
        return messageSource.getMessage(resolvableMessage, LocaleContextHolder.getLocale());
      } catch (NoSuchMessageException x) {
        // TODO add a debug message
      }
      try {
        return springErrorsMessageSource.getMessage(resolvableMessage,
            LocaleContextHolder.getLocale());
      } catch (NoSuchMessageException x) {
        // TODO add a debug message
      }
    }
    try {
      return springErrorsMessageSource.getMessage(
          new DefaultMessageSourceResolvable(getClass().getCanonicalName() + ".nomsg"),
          LocaleContextHolder.getLocale());
    } catch (NoSuchMessageException x) {
      assert false;
      return "No message available";
    }
  }

  protected List<ValidationError> getValidationErrors(
      ValidationFailedException validationFailedException) {
    Errors errors = validationFailedException.getErrors();
    List<ValidationError> validationErrors = new ArrayList<>();
    if (errors != null) {
      for (ObjectError objectError : errors.getAllErrors()) {
        ValidationError validationError = new ValidationError();
        validationError.setMessage(messageSource.getMessage(
            objectError, LocaleContextHolder.getLocale()));
        if (objectError instanceof FieldError) {
          FieldError fieldError = (FieldError)objectError;
          validationError.setField(fieldError.getField());
        }
        validationErrors.add(validationError);
      }
    }
    return validationErrors;
  }

  @Override
  public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes,
      boolean includeStackTrace) {

    Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes,
        includeStackTrace);

    Throwable error = getError(requestAttributes);

    if (error instanceof ApiException) {
      ApiException apiException = (ApiException)error;
      HttpStatus status = apiException.getHttpStatus();
      errorAttributes.put("status", status.value());
      errorAttributes.put("error", resolveMessage(apiException.getResolvableError()));
      if (!extendedErrorProperties.isIncludeException()) {
        errorAttributes.remove("exception");
      }
      errorAttributes.put("message", resolveMessage(apiException.getResolvableMessage()));

      if (apiException instanceof ValidationFailedException) {
        errorAttributes.put("validationErrors",
            getValidationErrors((ValidationFailedException)apiException));
      }

      // Change the HTTP status code to match the exception's HTTP status value
      requestAttributes.setAttribute("javax.servlet.error.status_code", status.value(), 0);
    }

    return errorAttributes;
  }
}

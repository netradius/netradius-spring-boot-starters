package com.netradius.spring.errors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

/**
 * Thrown when input validation fails. This will result in a 400 HTP status code getting sent to
 * the requesting client with the details of the validation failure.
 *
 * @author Erik R. Jensen
 */
public class ValidationFailedException extends ApiException {

  private final Errors errors;

  public ValidationFailedException(Errors errors) {
    super();
    this.errors = errors;
  }

  public ValidationFailedException(Errors errors, String message) {
    super(message);
    this.errors = errors;
  }

  public ValidationFailedException(Errors errors, String message, Object... arguments) {
    super(message, arguments);
    this.errors = errors;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }

  public Errors getErrors() {
    return errors;
  }

}

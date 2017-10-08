package com.netradius.spring.errors.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;

/**
 * Thrown when a method or action is attempted and has not yet been implemented. This will result
 * in 501 HTTP status code being sent to the requesting client.
 *
 * @author Erik R. Jensen
 */
public class NotImplementedException extends ApiException {

  public NotImplementedException() {}

  public NotImplementedException(String message) {
    super(message);
  }

  public NotImplementedException(String message, Object... arguments) {
    super(message, arguments);
  }

  public NotImplementedException(MessageSourceResolvable resolvableMessage) {
    super(resolvableMessage);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_IMPLEMENTED;
  }
}

package com.netradius.spring.errors.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;

/**
 * Thrown when a resource cannot be found. This will result in a 404 HTTP status code being
 * sent to the requesting client.
 *
 * @author Erik R. Jensen
 */
public class NotFoundException extends ApiException {

  public NotFoundException() {}

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Object... arguments) {
    super(message, arguments);
  }

  public NotFoundException(MessageSourceResolvable resolvableMessage) {
    super(resolvableMessage);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_FOUND;
  }

}

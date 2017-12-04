package com.netradius.spring.errors.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;

/**
 * Thrown when the requesting client lacks sufficient permissions to perform the requested action.
 * This will result in a 403 HTTP status code being sent to the requesting client.
 *
 * @author Erik R. Jensen
 */
public class ForbiddenException extends ApiException {

  public ForbiddenException() {}

  public ForbiddenException(String message) {
    super(message);
  }

  public ForbiddenException(String message, Object... arguments) {
    super(message, arguments);
  }

  public ForbiddenException(MessageSourceResolvable resolvableMessage) {
    super(resolvableMessage);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.FORBIDDEN;
  }

}

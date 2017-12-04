package com.netradius.spring.errors.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;

/**
 * Thrown when the requesting client fails authentication.
 * This will result in a 401 HTTP status code being sent to the client.
 *
 * @author Erik R. Jensen
 */
public class UnauthorizedException extends ApiException {

  public UnauthorizedException() {}

  public UnauthorizedException(String message) {
    super(message);
  }

  public UnauthorizedException(String message, Object... arguments) {
    super(message, arguments);
  }

  public UnauthorizedException(MessageSourceResolvable resolvableMessage) {
    super(resolvableMessage);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.UNAUTHORIZED;
  }
}

package com.netradius.spring.errors.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;

/**
 * Thrown when a request could not be processed because it was invalid in some way. This will
 * result in a 400 HTTP status code being sent to the requesting client.
 *
 * @author Erik R. Jensen
 */
public class BadRequestException extends ApiException {

  public BadRequestException() {}

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Object... arguments) {
    super(message, arguments);
  }

  public BadRequestException(MessageSourceResolvable resolvableMessage) {
    super(resolvableMessage);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }

}

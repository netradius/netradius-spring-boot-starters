package com.netradius.spring.errors.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;

/**
 * Base class for all API Exception types. This class is purposely defined as a checked
 * exception and not a runtime exception so these errors do not cause transactions to rollback.
 * Spring generally treats RuntimeException types as unrecoverable errors.
 *
 * @author Erik R. Jensen
 */
public abstract class ApiException extends Exception {

  private MessageSourceResolvable message;

  public ApiException() {
    this.message = new DefaultMessageSourceResolvable(
        getClass().getCanonicalName() + ".message");
  }

  public ApiException(String message) {
    this.message = new DefaultMessageSourceResolvable(
        new String[] {message}, null, null);
  }

  public ApiException(String message, Object... arguments) {
    this.message = new DefaultMessageSourceResolvable(
        new String[] {message}, arguments);
  }

  public ApiException(MessageSourceResolvable message) {
    this.message = message;
  }

  public abstract HttpStatus getHttpStatus();

  public MessageSourceResolvable getResolvableError() {
    return new DefaultMessageSourceResolvable(getClass().getCanonicalName() + ".error");
  }

  public MessageSourceResolvable getResolvableMessage() {
    return message;
  }

}

package com.netradius.spring.jwt.auth.exception;

/**
 * Base class for all JWT related exceptions.
 *
 * @author Erik R. Jensen
 */
public abstract class JwtException extends Exception {

  public JwtException() {}

  public JwtException(String msg) {
    super(msg);
  }

  public JwtException(Throwable t) {
    super(t);
  }

  public JwtException(String msg, Throwable t) {
    super(msg, t);
  }

}

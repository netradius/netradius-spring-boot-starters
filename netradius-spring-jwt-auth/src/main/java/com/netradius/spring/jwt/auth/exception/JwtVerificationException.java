package com.netradius.spring.jwt.auth.exception;

/**
 * Thrown when there is an error verifying a JWT.
 *
 * @author Erik R. Jensen
 */
public class JwtVerificationException extends JwtException {

  public JwtVerificationException(String msg, Throwable t) {
    super(msg, t);
  }

}

package com.netradius.spring.jwt.auth.exception;

/**
 * Thrown when there is an error generating a JWT.
 *
 * @author Erik R. Jensen
 */
public class JwtGenerationException extends JwtException {

  public JwtGenerationException(String msg, Throwable t) {
    super(msg, t);
  }

}

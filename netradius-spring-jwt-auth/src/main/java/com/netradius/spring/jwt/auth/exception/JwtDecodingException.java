package com.netradius.spring.jwt.auth.exception;

/**
 * Thrown when there is an error decoding a JWT.
 *
 * @author Erik R. Jensen
 */
public class JwtDecodingException extends JwtException {

  public JwtDecodingException(String msg, Throwable t) {
    super(msg, t);
  }

}

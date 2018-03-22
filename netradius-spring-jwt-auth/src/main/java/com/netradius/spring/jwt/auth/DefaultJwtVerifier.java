package com.netradius.spring.jwt.auth;

import com.netradius.spring.jwt.auth.exception.JwtVerificationException;

public class DefaultJwtVerifier implements JwtVerifier {

  @Override
  public VerifiedJwt verify(String jwt) throws JwtVerificationException {
    // TODO
    return null;
  }
}

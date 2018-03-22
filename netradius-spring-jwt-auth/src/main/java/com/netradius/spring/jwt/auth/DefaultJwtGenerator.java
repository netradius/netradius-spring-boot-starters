package com.netradius.spring.jwt.auth;

import com.netradius.spring.jwt.auth.exception.JwtGenerationException;

import java.util.Map;

public class DefaultJwtGenerator implements JwtGenerator {

  @Override
  public String generate(Map<String, Object> claims) throws JwtGenerationException {
    // TODO
    return null;
  }
}

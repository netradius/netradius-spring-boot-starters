package com.netradius.spring.jwt.auth;

import com.netradius.spring.jwt.auth.exception.JwtGenerationException;

import java.util.Map;

/**
 * Contract for JWT generators.
 *
 * @author Erik R. Jensen
 */
public interface JwtGenerator {

  /**
   * Generates a new JWT for the provided claims.
   *
   * @param claims the claims to encode
   * @return the generated JWT
   */
  String generate(Map<String, Object> claims) throws JwtGenerationException;

}

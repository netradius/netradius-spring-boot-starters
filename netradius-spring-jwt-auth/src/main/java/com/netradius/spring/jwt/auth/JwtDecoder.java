package com.netradius.spring.jwt.auth;

import com.netradius.spring.jwt.auth.exception.JwtDecodingException;

/**
 * Contract for JWT decoders. Instances of this interface should not attempt to validate the
 * JWT provided. They should simply parse the claims from the token.
 *
 * @author Erik R. Jensen
 */
public interface JwtDecoder {

  /**
   * Decodes a JWT.
   *
   * @param jwt the JWT to decode
   * @return the decoded JWT
   * @throws JwtDecodingException if there was an issue decoding the JWT
   */
  DecodedJwt decode(String jwt) throws JwtDecodingException;

}

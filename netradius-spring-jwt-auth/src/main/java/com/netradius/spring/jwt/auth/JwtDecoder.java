package com.netradius.spring.jwt.auth;

/**
 * Contract for JWT decoders. Instances of this interface should not attempt to validate the
 * JWT provided. They should simply parse the claims from the token.
 */
public interface JwtDecoder {

  DecodedJwt decode(String jwt) throws JwtException;

}

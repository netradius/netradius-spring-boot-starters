package com.netradius.spring.jwt.auth;

import com.netradius.spring.jwt.auth.exception.JwtDecodingException;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

public class DefaultJwtDecoder implements JwtDecoder {

  protected JwtConsumer consumer;

  public DefaultJwtDecoder() {
    consumer = new JwtConsumerBuilder()
        .setSkipAllValidators()
        .setDisableRequireSignature()
        .setSkipSignatureVerification()
        .build();
  }

  @Override
  public DecodedJwt decode(String jwt) throws JwtDecodingException {
    try {
      JwtClaims jwtClaims = consumer.processToClaims(jwt);
      return new DecodedJwt(jwt, jwtClaims.getClaimsMap());
    } catch (InvalidJwtException x) {
      throw new JwtDecodingException("Unable to parse jwt: " + x.getMessage(), x);
    }
  }

}

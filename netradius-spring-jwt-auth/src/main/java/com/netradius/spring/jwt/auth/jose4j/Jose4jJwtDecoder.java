package com.netradius.spring.jwt.auth.jose4j;

import com.netradius.spring.jwt.auth.DecodedJwt;
import com.netradius.spring.jwt.auth.JwtDecoder;
import com.netradius.spring.jwt.auth.JwtException;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

public class Jose4jJwtDecoder implements JwtDecoder {

  protected JwtConsumer consumer;

  public Jose4jJwtDecoder() {
    consumer = new JwtConsumerBuilder()
      .setSkipAllValidators()
      .setDisableRequireSignature()
      .setSkipSignatureVerification()
      .build();
  }

  @Override
  public DecodedJwt decode(String jwt) throws JwtException {
    try {
      JwtClaims jwtClaims = consumer.processToClaims(jwt);
      return new DecodedJwt(jwt, jwtClaims.getClaimsMap());
    } catch (InvalidJwtException x) {
      throw new JwtException("Unable to parse jwt: " + x.getMessage(), x);
    }
  }
}

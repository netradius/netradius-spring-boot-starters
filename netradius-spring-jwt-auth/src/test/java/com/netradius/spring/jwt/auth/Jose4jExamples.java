package com.netradius.spring.jwt.auth;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.security.Key;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Example code for using Jose4J. More examples are located at
 * https://bitbucket.org/b_c/jose4j/wiki/Home
 */
@Ignore
public class Jose4jExamples {

  @Test
  public void testJwks() throws IOException, JoseException {
    // Example showing how to pull data from a jwks endpoint.
    HttpsJwks httpsJwks = new HttpsJwks("https://auth0.auth0.com/.well-known/jwks.json");
    List<JsonWebKey> jsonWebKeys = httpsJwks.getJsonWebKeys();
    for (JsonWebKey jsonWebKey : jsonWebKeys) {
      System.out.println(jsonWebKey.getKeyId());
      System.out.println(jsonWebKey.getKeyType());
    }
  }

  @Test
  public void test() throws JoseException, InvalidJwtException, MalformedClaimException {
    // Example showing how to generate a jwt and then parse a jwt
    Key key = JwtHelper.generateHS512Key();

    JwtClaims claims = new JwtClaims();
    claims.setSubject("Scotty");
    claims.setExpirationTimeMinutesInTheFuture(5);

    JsonWebSignature jws = new JsonWebSignature();
    jws.setPayload(claims.toJson());
    jws.setKey(key);
    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA512);
    String token = jws.getCompactSerialization();

    JwtConsumer jwtConsumer = new JwtConsumerBuilder()
        .setSkipAllValidators()
        .setDisableRequireSignature()
        .setSkipSignatureVerification()
        .build();

    JwtClaims parsedClaims = jwtConsumer.processToClaims(token);
    assertThat(parsedClaims.getSubject(), equalTo("Scotty"));

    JwtConsumer validatingJwtConsumer = new JwtConsumerBuilder()
        .setRequireSubject()
        .setRequireExpirationTime()
        .setAllowedClockSkewInSeconds(30)
        .setExpectedSubject("Scotty")
        .setVerificationKey(key)
        .setJwsAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.WHITELIST,
            AlgorithmIdentifiers.HMAC_SHA512))
        .build();

    JwtClaims validatedClaims = validatingJwtConsumer.processToClaims(token);
    assertThat(validatedClaims.getSubject(), equalTo("Scotty"));

  }

}

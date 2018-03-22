package com.netradius.spring.jwt.auth;

import com.netradius.spring.jwt.auth.exception.JwtVerificationException;

/**
 * Contract for all JWT verifiers.
 *
 * @author Erik R. Jensen
 */
public interface JwtVerifier {

  VerifiedJwt verify(String jwt) throws JwtVerificationException;

}

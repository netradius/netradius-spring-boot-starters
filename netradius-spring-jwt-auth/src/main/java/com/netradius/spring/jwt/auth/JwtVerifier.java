package com.netradius.spring.jwt.auth;

public interface JwtVerifier {

  VerifiedJwt verify(String jwt);

}

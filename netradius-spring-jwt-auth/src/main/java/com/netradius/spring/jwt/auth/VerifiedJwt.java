package com.netradius.spring.jwt.auth;

import java.util.Map;

public class VerifiedJwt extends DecodedJwt {

  // TODO

  public VerifiedJwt(String jwt, Map<String, Object> claims) {
    super(jwt, claims);
  }

}

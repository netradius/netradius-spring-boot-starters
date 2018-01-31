package com.netradius.spring.jwt.auth;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DecodedJwt implements Serializable {

  protected String jwt;
  private Map<String, Object> claims;

  public DecodedJwt(String jwt, Map<String, Object> claims) {
    this.jwt = jwt;
    this.claims = claims;
  }

  public String getJwt() {
    return jwt;
  }

  public Map<String, Object> getClaims() {
    return claims;
  }

  public String getIssuer() {
    return (String)claims.get("iss");
  }

  public String getSubject() {
    return (String)claims.get("sub");
  }

  public String getAudience() {
    return (String)claims.get("aud");
  }

  public Number getExpiration() {
    return (Number)claims.get("exp");
  }

  public Number getNotBefore() {
    return (Number)claims.get("nbf");
  }

  public Number getIssuedAt() {
    return (Number)claims.get("iat");
  }

  public String getIdentifier() {
    return (String)claims.get("jti");
  }

  @SuppressWarnings("unchecked")
  public List<String> getScope() {
    String str = (String)claims.get("scope");
    if (StringUtils.hasText(str)) {
      return Arrays.asList(str.split(" "));
    }
    return Collections.EMPTY_LIST;
  }
}

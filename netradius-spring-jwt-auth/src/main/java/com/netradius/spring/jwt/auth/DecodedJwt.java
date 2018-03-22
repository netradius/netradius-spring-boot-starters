package com.netradius.spring.jwt.auth;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Holds data from a decoded JWT.
 *
 * @author Erik R. Jensen
 */
public class DecodedJwt implements Serializable {

  protected String jwt;
  private Map<String, Object> claims;

  public DecodedJwt(String jwt, Map<String, Object> claims) {
    this.jwt = jwt;
    this.claims = claims;
  }

  /**
   * Returns the original encoded JWT.
   *
   * @return the jwt
   */
  public String getJwt() {
    return jwt;
  }

  /**
   * Returns the decoded claims for this JWT.
   *
   * @return the claims
   */
  public Map<String, Object> getClaims() {
    return claims;
  }

  /**
   * Standard JWT claim identifying the principal that issued the JWT.
   *
   * @return the issuer
   */
  public String getIssuer() {
    return (String)claims.get("iss");
  }

  /**
   * Standard JWT claim identifying the principal that is the subject of the JWT.
   *
   * @return the subject
   */
  public String getSubject() {
    return (String)claims.get("sub");
  }

  /**
   * Standard JWT claim identifying the recipients this JWT is intended for.
   *
   * @return the audience
   */
  public String getAudience() {
    return (String)claims.get("aud");
  }

  /**
   * Standard JWT claim identifying the expiration time on which the JWT is expired.
   *
   * @return the expiration time
   */
  public Number getExpiration() {
    return (Number)claims.get("exp");
  }

  /**
   * Standard JWT claim identifying the time before which the JWT must not be accepted.
   *
   * @return the not before time
   */
  public Number getNotBefore() {
    return (Number)claims.get("nbf");
  }

  /**
   * Standard JWT claim identifying the time at which the JWT was issued.
   *
   * @return the issued time
   */
  public Number getIssuedAt() {
    return (Number)claims.get("iat");
  }

  /**
   * Standard JWT claim which provided a unique identifier for the JWT.
   *
   * @return the identifier
   */
  public String getIdentifier() {
    return (String)claims.get("jti");
  }

  /**
   * Returns a parsed list of scope claims.
   *
   * @return the scope
   */
  @SuppressWarnings("unchecked")
  public List<String> getScope() {
    String str = (String)claims.get("scope");
    if (StringUtils.hasText(str)) {
      return Arrays.asList(str.split(" "));
    }
    return Collections.EMPTY_LIST;
  }
}

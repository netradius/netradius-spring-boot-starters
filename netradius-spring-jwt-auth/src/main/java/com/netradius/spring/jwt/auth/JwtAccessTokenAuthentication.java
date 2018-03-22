package com.netradius.spring.jwt.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Authentication class for Spring Security which wraps a DecodedJwt instance.
 *
 * @author Erik R. Jensen
 */
public class JwtAccessTokenAuthentication implements Authentication {

  protected boolean authenticated = false;
  protected DecodedJwt decodedJwt;
  protected List<GrantedAuthority> grantedAuthorities;

  /**
   * Creates a new JwtAccessTokenAuthentication instance.
   *
   * @param decodedJwt the decoded JWT
   */
  public JwtAccessTokenAuthentication(DecodedJwt decodedJwt) {
    this.decodedJwt = decodedJwt;
  }

  /**
   * Returns the decoded JWT.
   *
   * @return the decoded JWT
   */
  public DecodedJwt getDecodedJwt() {
    return decodedJwt;
  }

  /**
   * Returns the parsed granted authorities from the decoded JWT.
   *
   * @return the granted authorities
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (grantedAuthorities == null) {
      grantedAuthorities = decodedJwt.getScope().stream()
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
    }
    return grantedAuthorities;
  }

  /**
   * Returns the decoded JWT as the credentials.
   *
   * @return the decoded JWT
   */
  @Override
  public Object getCredentials() {
    return decodedJwt;
  }

  /**
   * Returns the decoded JWT as the authentication details.
   *
   * @return the decoded JWT
   */
  @Override
  public Object getDetails() {
    return decodedJwt;
  }

  /**
   * Returns the subject in the JWT as the principal.
   *
   * @return the subject
   */
  @Override
  public Object getPrincipal() {
    return decodedJwt.getSubject();
  }

  /**
   * Checks if the access has been verified and is valid.
   *
   * @return true if authenticated, false if otherwise
   */
  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  /**
   * Sets this access token as verified and valid.
   *
   * @param authenticated true if authenticated, false if otherwise
   * @throws IllegalArgumentException if an error occurs
   */
  @Override
  public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
    this.authenticated = authenticated;
  }

  /**
   * Returns the subject in the JWT as the name.
   *
   * @return the subject
   */
  @Override
  public String getName() {
    return decodedJwt.getSubject();
  }
}

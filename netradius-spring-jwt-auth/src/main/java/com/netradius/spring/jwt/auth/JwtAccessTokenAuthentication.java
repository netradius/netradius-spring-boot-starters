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

  public JwtAccessTokenAuthentication(DecodedJwt decodedJwt) {
    this.decodedJwt = decodedJwt;
  }

  public DecodedJwt getDecodedJwt() {
    return decodedJwt;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (grantedAuthorities == null) {
      grantedAuthorities = decodedJwt.getScope().stream()
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
    }
    return grantedAuthorities;
  }

  @Override
  public Object getCredentials() {
    return decodedJwt;
  }

  @Override
  public Object getDetails() {
    return decodedJwt;
  }

  @Override
  public Object getPrincipal() {
    return decodedJwt.getSubject();
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
    this.authenticated = authenticated;
  }

  @Override
  public String getName() {
    return decodedJwt.getSubject();
  }
}

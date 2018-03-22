package com.netradius.spring.jwt.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * A custom AuthenticationProvider which will verify instances of JwtAccessTokenAuthentication.
 *
 * @author Erik R. Jensen
 */
public class JwtAccessTokenAuthenticationProvider implements AuthenticationProvider {

  protected JwtVerifier jwtVerifier;

  public JwtAccessTokenAuthenticationProvider(JwtVerifier jwtVerifier) {
    this.jwtVerifier = jwtVerifier;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (supports(authentication.getClass())) {
      JwtAccessTokenAuthentication jwtAccessTokenAuthentication =
          (JwtAccessTokenAuthentication) authentication;
      VerifiedJwt verifiedJwt = jwtVerifier
          .verify(jwtAccessTokenAuthentication.getDecodedJwt().getJwt());
      // TODO
      return new JwtAccessTokenAuthentication(verifiedJwt);
    }
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return JwtAccessTokenAuthentication.class.isAssignableFrom(authentication);
  }
}

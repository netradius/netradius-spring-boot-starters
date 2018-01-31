package com.netradius.spring.jwt.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is responsible for obtaining the JWT access token from the request. This is done
 * in one of the following ways:
 *
 * <ul>
 *   <li>Using the Authorization: Bearer [token] HTTP header as used with OAuth2</li>
 *   <li>Using a cookie by default named "access_token"</li>
 * </ul>
 *
 * @author Erik R. Jensen
 */
public class JwtAccessTokenAuthenticationProcessingFilter
    extends AbstractAuthenticationProcessingFilter {

  protected JwtDecoder jwtDecoder;

  public JwtAccessTokenAuthenticationProcessingFilter(JwtDecoder jwtDecoder) {
    super("/*");
    this.jwtDecoder = jwtDecoder;
  }

  /**
   * Obtains the JWT token from a cookie on the request.
   *
   * @param request the request
   * @return the JWT token or null if none could be found
   */
  protected String getJwtFromCookie(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, "access_token");
    String value = cookie == null ? null : cookie.getValue();
    return StringUtils.hasText(value) ? value.trim() : null;
  }

  /**
   * Obtains the JWT token from the Authorization header on the request.
   *
   * @param request the request
   * @return the JWT token or null if none could be found
   */
  protected String getJwtFromHeader(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if (StringUtils.hasText(header)) {
      header = header.trim();
      if (header.startsWith("Bearer ") && header.length() > 7) {
        header = header.substring(7, header.length()).trim();
        return StringUtils.hasText(header) ? header : null;
      }
    }
    return null;
  }

  /**
   * Obtains the JWT token from the request.
   *
   * @param request the request
   * @return the JWT token or null if none could be found
   */
  protected String getJwtFromRequest(HttpServletRequest request) {
    String token = getJwtFromHeader(request);
    return token != null ? token : getJwtFromCookie(request);
  }

  /**
   * Attempts to authenticate a request using a provided JWT access token.
   *
   * @param request the request
   * @param response the response
   * @return the authenticated user token, or null if authentication is incomplete.
   * @throws AuthenticationException if an error occurs
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    String jwt = getJwtFromRequest(request);
    if (jwt != null) {

      DecodedJwt decodedJwt = jwtDecoder.decode(jwt);
      JwtAccessTokenAuthentication authentication = new JwtAccessTokenAuthentication(decodedJwt);

      return getAuthenticationManager().authenticate(authentication);
    }
    return null;
  }
}

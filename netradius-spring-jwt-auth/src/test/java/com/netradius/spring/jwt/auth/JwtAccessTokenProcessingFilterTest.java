package com.netradius.spring.jwt.auth;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.junit.Before;
import org.junit.Test;

import java.security.Key;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtAccessTokenProcessingFilterTest {

  private Key key;
  private String token;
  private String token2;
  private JwtDecoder jwtDecoder;

  @Before
  public void before() throws JoseException {
    jwtDecoder = new DefaultJwtDecoder();
    key = JwtHelper.generateHS512Key();

    JwtClaims claims = new JwtClaims();
    claims.setSubject("Kirk");

    JsonWebSignature jws = new JsonWebSignature();
    jws.setPayload(claims.toJson());
    jws.setKey(key);
    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA512);
    token = jws.getCompactSerialization();
    System.out.println("Token: " + token);

    claims = new JwtClaims();
    claims.setSubject("Spock");

    jws = new JsonWebSignature();
    jws.setPayload(claims.toJson());
    jws.setKey(key);
    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA512);
    token2 = jws.getCompactSerialization();
    System.out.println("Token2: " + token2);
  }

  @Test
  public void testGetJwtFromCookie() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    Cookie[] cookies = new Cookie[1];
    cookies[0] = new Cookie("access_token", token);
    when(request.getCookies()).thenReturn(cookies);
    JwtAccessTokenAuthenticationProcessingFilter filter =
        new JwtAccessTokenAuthenticationProcessingFilter(jwtDecoder);
    assertThat(filter.getJwtFromCookie(request), equalTo(token));
  }

  @Test
  public void testGetJwtFromCookieEmpty() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    Cookie[] cookies = new Cookie[1];
    cookies[0] = new Cookie("access_token", "");
    when(request.getCookies()).thenReturn(cookies);
    JwtAccessTokenAuthenticationProcessingFilter filter =
        new JwtAccessTokenAuthenticationProcessingFilter(jwtDecoder);
    assertThat(filter.getJwtFromCookie(request), nullValue());
  }

  @Test
  public void testGetJwtFromHeader() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    JwtAccessTokenAuthenticationProcessingFilter filter =
        new JwtAccessTokenAuthenticationProcessingFilter(jwtDecoder);
    assertThat(filter.getJwtFromHeader(request), equalTo(token));
  }

  @Test
  public void testGetJwtFromHeaderEmpty() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn("Bearer ");
    JwtAccessTokenAuthenticationProcessingFilter filter =
        new JwtAccessTokenAuthenticationProcessingFilter(jwtDecoder);
    assertThat(filter.getJwtFromHeader(request), nullValue());
  }

  @Test
  public void testGetJwtFromRequestOrder() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    Cookie[] cookies = new Cookie[1];
    cookies[0] = new Cookie("access_token", token2);
    when(request.getCookies()).thenReturn(cookies);
    JwtAccessTokenAuthenticationProcessingFilter filter =
        new JwtAccessTokenAuthenticationProcessingFilter(jwtDecoder);
    assertThat(filter.getJwtFromRequest(request), equalTo(token));
  }

}

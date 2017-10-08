package com.netradius.spring.errors.web.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Extended version of the HttpClientErrorException that can return a parsed ErrorResponse.
 *
 * @author Erik R. Jensen
 */
public class ExtendedHttpClientErrorException extends HttpClientErrorException {

  private ErrorResponseParser errorResponseParser;

  public ExtendedHttpClientErrorException(ErrorResponseParser errorResponseParser,
      HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody,
      Charset responseCharset) {
    super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
    this.errorResponseParser = errorResponseParser;
  }

  public ErrorResponse getErrorResponse() throws IOException {
    return errorResponseParser.parse(getResponseHeaders(), getResponseBodyAsByteArray());
  }
}

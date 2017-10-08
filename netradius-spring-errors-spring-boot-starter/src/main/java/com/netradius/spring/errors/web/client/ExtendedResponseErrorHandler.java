package com.netradius.spring.errors.web.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

/**
 * Overrides the standard error handling in DefaultResponseErrorHandler to make an attempt to
 * parse the response body of the error for easier processing by the calling code. This class
 * is purely intended simplify and reduce the amount of code required by a client consuming a
 * rest endpoint.
 *
 * @author Erik R. Jensen
 */
public class ExtendedResponseErrorHandler extends DefaultResponseErrorHandler {

  protected ErrorResponseParser errorResponseParser;

  public ExtendedResponseErrorHandler(ErrorResponseParser errorResponseParser) {
    this.errorResponseParser = errorResponseParser;
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    HttpStatus statusCode = getHttpStatusCode(response);
    switch (statusCode.series()) {
      case CLIENT_ERROR:
        throw new ExtendedHttpClientErrorException(errorResponseParser, statusCode,
            response.getStatusText(), response.getHeaders(), getResponseBody(response),
            getCharset(response));
      case SERVER_ERROR:
        throw new ExtendedHttpServerErrorException(errorResponseParser, statusCode,
            response.getStatusText(), response.getHeaders(), getResponseBody(response),
            getCharset(response));
      default:
        throw new RestClientException("Unknown status code [" + statusCode + "]");
    }
  }
}

package com.netradius.spring.errors.web.client;

import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.Serializable;

/**
 * Contract for ErrorResponseParser instances. Be aware it's expected all parsers will
 * be Serializable.
 *
 * @author Erik R. Jensen
 */
public interface ErrorResponseParser extends Serializable {

  ErrorResponse parse(HttpHeaders headers, byte[] responseBody) throws IOException;

}

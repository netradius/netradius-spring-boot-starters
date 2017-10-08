package com.netradius.spring.errors.web.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * This class attempts to parse the provided response body to an ErrorResponse object. An attempt
 * will only be made to parse the body if the subtype content type in the header start with
 * json or ends with +json like application/json or application/xxx+json.
 *
 * @author Erik R. Jensen
 */
public class JsonErrorResponseParser implements ErrorResponseParser {

  protected ObjectMapper objectMapper;

  public JsonErrorResponseParser(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public ErrorResponse parse(HttpHeaders headers, byte[] responseBody)
      throws IOException {
    MediaType contentType = headers.getContentType();
    if (contentType != null) {
      String subtype = contentType.getSubtype();
      if (subtype.startsWith("json") || subtype.endsWith("+json")) {
        return objectMapper.readValue(responseBody, ErrorResponse.class);
      }
    }
    return null;
  }
}

package com.netradius.spring.errors.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netradius.spring.errors.web.client.ErrorResponse;
import com.netradius.spring.errors.web.client.ExtendedHttpClientErrorException;
import com.netradius.spring.errors.web.client.ExtendedHttpServerErrorException;
import com.netradius.spring.errors.web.client.ExtendedResponseErrorHandler;
import com.netradius.spring.errors.web.client.JsonErrorResponseParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class SampleControllerTest {

  @LocalServerPort
  private int port;

  private String baseUrl;
  private RestTemplate restTemplate;
  private ObjectMapper objectMapper;

  @Before
  public void setup() {
    baseUrl = "http://localhost:" + port + "/";
    objectMapper = new ObjectMapper();
    restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(
        new ExtendedResponseErrorHandler(new JsonErrorResponseParser(objectMapper)));
  }

  @Test(expected = ExtendedHttpClientErrorException.class)
  public void testNotFound() throws IOException {
    try {
      restTemplate.getForEntity(baseUrl + "NotFound", ExampleMessage.class);
    } catch (ExtendedHttpClientErrorException x) {
      assertThat(x.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
      ErrorResponse errorResponse = x.getErrorResponse();
      assertThat(errorResponse.getError(), equalTo("Not Found"));
      assertThat(errorResponse.getMessage(),
          equalTo("The requested resource was not found"));
      throw x;
    }
  }

  @Test(expected = ExtendedHttpServerErrorException.class)
  public void testNotImplemented() throws IOException {
    try {
      restTemplate.getForEntity(baseUrl + "NotImplemented", ExampleMessage.class);
    } catch (ExtendedHttpServerErrorException x) {
      assertThat(x.getStatusCode(), equalTo(HttpStatus.NOT_IMPLEMENTED));
      ErrorResponse errorResponse = x.getErrorResponse();
      assertThat(errorResponse.getError(), equalTo("Not Implemented"));
      assertThat(errorResponse.getMessage(),
          equalTo("The requested action has not been implemented"));
      throw x;
    }
  }

  @Test(expected = ExtendedHttpClientErrorException.class)
  public void testForbidden() throws IOException {
    try {
      restTemplate.getForEntity(baseUrl + "Forbidden", ExampleMessage.class);
    } catch (ExtendedHttpClientErrorException x) {
      assertThat(x.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
      ErrorResponse errorResponse = x.getErrorResponse();
      assertThat(errorResponse.getError(), equalTo("Forbidden"));
      assertThat(errorResponse.getMessage(),
          equalTo("Insufficient permissions to perform the requested action"));
      throw x;
    }
  }

}

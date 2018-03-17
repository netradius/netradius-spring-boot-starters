package com.netradius.spring.errors.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netradius.spring.errors.web.client.ErrorResponse;
import com.netradius.spring.errors.web.client.ExtendedHttpClientErrorException;
import com.netradius.spring.errors.web.client.ExtendedResponseErrorHandler;
import com.netradius.spring.errors.web.client.JsonErrorResponseParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Provides unit tests for SampleMessageController to show how validation failures work.
 *
 * @author Erik R. Jensen
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class SendMessageControllerTest {

  @LocalServerPort
  private int port;

  private String baseUrl;
  private Random random;
  private ObjectMapper objectMapper;
  private RestTemplate restTemplate;

  @Before
  public void setup() {
    baseUrl = "http://localhost:" + port + "/";
    random = new Random();
    objectMapper = new ObjectMapper();
    restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(
        new ExtendedResponseErrorHandler(new JsonErrorResponseParser(objectMapper)));
  }

  private String generateRandomString(int len) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < len; i++) {
      char c = (char)(random.nextInt(25) + 25);
      sb.append(c);
    }
    return sb.toString();
  }

  @Test(expected = ExtendedHttpClientErrorException.class)
  public void testEmptyValidationFailure() throws IOException {
    SampleMessageForm form = new SampleMessageForm();
    try {
      ResponseEntity<Void> responseEntity = restTemplate.postForEntity(
          baseUrl + "send_message", form, Void.class);
    } catch (ExtendedHttpClientErrorException x) {
      assertThat(x.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
      ErrorResponse errorResponse = x.getErrorResponse();
      assertThat(errorResponse.getValidationErrors("message").get(0).getMessage(),
          equalTo("may not be empty"));
      assertThat(errorResponse.getValidationErrors("subject").get(0).getMessage(),
          equalTo("may not be empty"));
      assertThat(errorResponse.getValidationErrors("recipients").get(0).getMessage(),
          equalTo("may not be empty"));
      throw x;
    }
  }

  @Test(expected = ExtendedHttpClientErrorException.class)
  public void testComplexValidationFailure() throws IOException {
    SampleMessageForm form = new SampleMessageForm();
    form.setSubject(generateRandomString(51)); // too long
    form.setMessage(generateRandomString(10001)); // too long
    for (int i = 0; i < 2; i++) {
      SampleMessageForm.Recipient recipient = new SampleMessageForm.Recipient();
      recipient.setEmail(generateRandomString(10)); // not a valid email
      recipient.setName(generateRandomString(31)); // too long
      form.getRecipients().add(recipient);
    }
    try {
      ResponseEntity<Void> responseEntity = restTemplate.postForEntity(
          baseUrl + "send_message", form, Void.class);
    } catch (ExtendedHttpClientErrorException x) {
      assertThat(x.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
      ErrorResponse errorResponse = x.getErrorResponse();
      assertThat(errorResponse.getValidationErrors("message").size(), equalTo(1));
      assertThat(errorResponse.getValidationErrors("subject").size(), equalTo(1));
      assertThat(errorResponse.getValidationErrors("recipients[0].name").size(), equalTo(1));
      assertThat(errorResponse.getValidationErrors("recipients[0].email").size(), equalTo(1));
      assertThat(errorResponse.getValidationErrors("recipients[1].name").size(), equalTo(1));
      assertThat(errorResponse.getValidationErrors("recipients[1].email").size(), equalTo(1));
      throw x;
    }
  }

}

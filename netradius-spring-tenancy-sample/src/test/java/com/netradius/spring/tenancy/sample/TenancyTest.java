package com.netradius.spring.tenancy.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class TenancyTest {

  private static final String TENANT1 = "sample1";
  private static final String TENANT2 = "sample2";

  @LocalServerPort
  private int port;

  private String baseUrl;
  private String tenant1Url;
  private String tenant2Url;
  private RestTemplate restTemplate;

  @Before
  public void setup() {
    baseUrl = "http://localhost:" + port + "/";
    tenant1Url = "http://" + TENANT1 + ".localdev.netradius.net:" + port + "/";
    tenant2Url = "http://" + TENANT2 + ".localdev.netradius.net:" + port + "/";
    restTemplate = new RestTemplate();
  }

  @Test
  public void test() {
    // Create tenant1
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("tenant", TENANT1);
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    String[] tenants = restTemplate.postForObject(baseUrl + "tenants", request, String[].class);
    assertTrue(Arrays.asList(tenants).contains(TENANT1));

    // Create tenant2
    params = new LinkedMultiValueMap<>();
    params.add("tenant", TENANT2);
    request = new HttpEntity<>(params, headers);
    tenants = restTemplate.postForObject(baseUrl + "tenants", request, String[].class);
    assertTrue(Arrays.asList(tenants).contains(TENANT2));

    // Add actor to tenant1
    params = new LinkedMultiValueMap<>();
    params.add("name", "Leonard Nemoy");
    request = new HttpEntity<>(params, headers);
    String[] actors = restTemplate.postForObject(tenant1Url + "actors", request, String[].class);
    assertTrue(Arrays.asList(actors).contains("Leonard Nemoy"));

    // Add actor to tenant2
    params = new LinkedMultiValueMap<>();
    params.add("name", "William Shatner");
    request = new HttpEntity<>(params, headers);
    actors = restTemplate.postForObject(tenant2Url + "actors", request, String[].class);
    assertTrue(Arrays.asList(actors).contains("William Shatner"));

    // Validate tenant1 does not container tenant2's actor and vice versa
    actors = restTemplate.getForObject(tenant1Url + "actors", String[].class);
    assertFalse(Arrays.asList(actors).contains("William Shatner"));
    actors = restTemplate.getForObject(tenant2Url + "actors", String[].class);
    assertFalse(Arrays.asList(actors).contains("Leonard Nemoy"));

    // Delete tenant1
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + "tenants")
        .queryParam("tenant", TENANT1);
    restTemplate.delete(builder.build().toUriString());

    // Delete tenant2
    builder = UriComponentsBuilder.fromUriString(baseUrl + "tenants")
        .queryParam("tenant", TENANT2);
    restTemplate.delete(builder.build().toUriString());
  }

}

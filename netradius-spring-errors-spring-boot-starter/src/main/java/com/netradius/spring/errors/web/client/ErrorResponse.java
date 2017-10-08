package com.netradius.spring.errors.web.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Holds parsed error response data.
 *
 * @author Erik R. Jensen
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
  protected int status;
  protected String error;
  protected String message;
  protected String exception;
  protected String path;
  protected String trace;
  protected List<ValidationError> validationErrors = new ArrayList<>();

  public List<ValidationError> getValidationErrors(String field) {
    return validationErrors.stream()
        .filter(e -> e.getField().equals(field))
        .collect(Collectors.toList());
  }
}

package com.netradius.spring.errors.web.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Holds parsed validation error data.
 *
 * @author Erik R. Jensen
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationError {
  protected String field;
  protected String message;
}

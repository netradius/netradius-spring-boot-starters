package com.netradius.spring.errors.web;

import lombok.Data;

@Data
public class ValidationError {
  private String field;
  private String message;
}

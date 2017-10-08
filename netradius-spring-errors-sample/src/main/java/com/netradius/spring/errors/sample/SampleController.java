package com.netradius.spring.errors.sample;

import com.netradius.spring.errors.exception.ApiException;
import com.netradius.spring.errors.exception.ForbiddenException;
import com.netradius.spring.errors.exception.NotFoundException;
import com.netradius.spring.errors.exception.NotImplementedException;
import com.netradius.spring.errors.exception.ValidationFailedException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  @RequestMapping("/NotFound")
  public ExampleMessage notFound() throws ApiException {
    throw new NotFoundException();
  }

  @RequestMapping("/NotImplemented")
  public ExampleMessage notImplemented() throws ApiException {
    throw new NotImplementedException();
  }

  @RequestMapping("/Forbidden")
  public ExampleMessage forbidden() throws ApiException {
    throw new ForbiddenException();
  }

}

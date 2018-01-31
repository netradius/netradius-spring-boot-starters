package com.neradius.spring.jwt.auth.sample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  @RequestMapping("/hello")
  public String hello() {
    return "hello";
  }

}

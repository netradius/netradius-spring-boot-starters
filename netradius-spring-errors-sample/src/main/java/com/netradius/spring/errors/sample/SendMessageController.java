package com.netradius.spring.errors.sample;

import com.netradius.spring.errors.exception.ApiException;
import com.netradius.spring.errors.exception.ValidationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Slf4j
public class SendMessageController {

  @RequestMapping(value = "/send_message", method = POST)
  @ResponseStatus(HttpStatus.OK)
  public void sendMessage(@Valid @RequestBody SampleMessageForm form, Errors errors)
      throws ApiException {
    if (errors.hasErrors()) {
      throw new ValidationFailedException(errors);
    }
    // This is where you would do something cool, but we're not that cool
    log.info("Received message: " + form.toString());
  }

}

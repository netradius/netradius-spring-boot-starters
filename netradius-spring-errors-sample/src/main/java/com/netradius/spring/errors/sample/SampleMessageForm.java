package com.netradius.spring.errors.sample;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@Data
public class SampleMessageForm {

  @Data
  public static class Recipient {

    @NotEmpty
    @Length(max = 30)
    private String name;

    @Email
    @Length(max = 255)
    private String email;

  }

  @NotEmpty
  @Length(max = 50)
  private String subject;

  @NotEmpty
  @Length(max = 10000)
  private String message;

  @NotEmpty
  @Valid
  public List<Recipient> recipients = new ArrayList<>();

}

package com.netradius.spring.errors.autoconfigure;

import com.netradius.spring.errors.context.SpringErrorsMessageSource;
import com.netradius.spring.errors.web.ExtendedErrorController;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

/**
 * Handles auto configuration for API error handling.
 *
 * @author Erik R. Jensen
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@AutoConfigureBefore({ErrorMvcAutoConfiguration.class}) // Must load ErrorAttributes first
@EnableConfigurationProperties({ExtendedErrorProperties.class, ServerProperties.class})
public class SpringErrorsAutoConfiguration {
  // See spring-boot-autoconfigure ... ErrorMvcAutoConfiguration.java

  private final ServerProperties serverProperties;
  private final ExtendedErrorProperties extendedErrorProperties;
  private final MessageSource messageSource;

  public SpringErrorsAutoConfiguration(ServerProperties serverProperties,
      ExtendedErrorProperties extendedErrorProperties, MessageSource messageSource) {
    this.serverProperties = serverProperties;
    this.extendedErrorProperties = extendedErrorProperties;
    this.messageSource = messageSource;
  }

  @Bean
  @ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
  // TODO When Spring Boot 2.0 is released, they have a boolean to include exception
  public ErrorAttributes errorAttributes() {
    return new ExtendedErrorAttributes(
        extendedErrorProperties, springErrorsMessageSource(), messageSource);
  }

  @Bean
  @ConditionalOnMissingBean(value = ExtendedErrorController.class, search = SearchStrategy.CURRENT)
  public ExtendedErrorController errorController() {
    return new ExtendedErrorController(serverProperties.getError());
  }

  @Bean
  @ConditionalOnMissingBean(value = SpringErrorsMessageSource.class,
      search = SearchStrategy.CURRENT)
  public SpringErrorsMessageSource springErrorsMessageSource() {
    return new SpringErrorsMessageSource();
  }

}

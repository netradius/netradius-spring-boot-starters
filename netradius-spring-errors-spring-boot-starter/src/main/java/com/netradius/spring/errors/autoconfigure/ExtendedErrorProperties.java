package com.netradius.spring.errors.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for extended web error handling provided by netradius-spring-errors.
 *
 * @author Erik R. Jensen
 */
@ConfigurationProperties(prefix = "extended.server.error", ignoreUnknownFields = true)
public class ExtendedErrorProperties {

  private boolean includeException = false;

  public boolean isIncludeException() {
    return includeException;
  }

  public void setIncludeException(boolean includeException) {
    this.includeException = includeException;
  }

}

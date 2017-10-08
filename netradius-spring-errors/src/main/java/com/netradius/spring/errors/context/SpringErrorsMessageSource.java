package com.netradius.spring.errors.context;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * Simple wrapper around a ResourceBundleMessageSource to retrieve error messages built
 * into this library. This was done to avoid duplicate MessageSource objects which could
 * cause confusion and problems with spring boot applications.
 *
 * @author Erik R. Jensen
 */
public class SpringErrorsMessageSource {

  protected ResourceBundleMessageSource internalMessageSource;

  public SpringErrorsMessageSource() {
    internalMessageSource = new ResourceBundleMessageSource();
    internalMessageSource.setBasename("com.netradius.spring.errors.messages");
    internalMessageSource.setCacheMillis(-1);
    internalMessageSource.setAlwaysUseMessageFormat(true);
    internalMessageSource.setFallbackToSystemLocale(true);
  }

  public String getMessage(MessageSourceResolvable resolvableMessage, Locale locale)
      throws NoSuchMessageException {
    return internalMessageSource.getMessage(resolvableMessage, locale);
  }

}

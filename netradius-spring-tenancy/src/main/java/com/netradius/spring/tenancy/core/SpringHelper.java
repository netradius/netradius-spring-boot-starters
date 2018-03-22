package com.netradius.spring.tenancy.core;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The use of this class should be avoided as much as possible. The methods provided in this helper
 * are to help ease non-spring managed objects. This class requires
 *
 * @author Erik R. Jensen
 */
@WebListener
public class SpringHelper implements ServletContextListener {

  private static ServletContext servletContext;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    servletContext = servletContextEvent.getServletContext();
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    servletContext = null;
  }

  public static ServletContext getServletContext() {
    return servletContext;
  }

  public static WebApplicationContext getWebApplicationContext() {
    return WebApplicationContextUtils.getWebApplicationContext(getServletContext());
  }

  public static <T> T getBean(Class<T> clazz) {
    return getWebApplicationContext().getBean(clazz);
  }

}

package com.netradius.spring.errors.web;

import com.netradius.spring.errors.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is responsible for handling all thrown ApiExceptions and forwarding them to the
 * BasicErrorController in Spring Boot. The BasicErrorController will process the exception by
 * gathering all the error attributes and returning them as a Map&lt;String,String&gt;. This is
 * further customized in ExtendedErrorAttributes in this library.
 *
 * @author Erik R. Jensen
 */
@RestControllerAdvice
public class ExtendedErrorController {

  private final ErrorProperties errorProperties;

  public ExtendedErrorController(ErrorProperties errorProperties) {
    this.errorProperties = errorProperties;
  }

  /**
   * Handles all instances of ApiException.
   *
   * @param req the request
   * @param res the response
   * @throws IOException if an I/O error occurs
   * @throws ServletException of a Servlet error occurs
   */
  @ExceptionHandler(ApiException.class)
  public void handleApiException(HttpServletRequest req, HttpServletResponse res)
      throws IOException, ServletException {
    req.getRequestDispatcher(errorProperties.getPath()).forward(req, res);
  }

}

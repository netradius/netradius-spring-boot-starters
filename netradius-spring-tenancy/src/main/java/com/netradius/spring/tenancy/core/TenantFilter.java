package com.netradius.spring.tenancy.core;

import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter used to identify the tenant making the request.
 *
 * @author Erik R. Jensen
 */
@Order(0)
public class TenantFilter extends OncePerRequestFilter {

  private TenantService tenantService;

  public TenantFilter(TenantService tenantService) {
    this.tenantService = tenantService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain)
      throws ServletException, IOException {
    try {
      String tenant = req.getServerName();
      int idx = tenant.indexOf(".");
      tenant = idx != -1 && idx != tenant.lastIndexOf(".")
          ? tenant.substring(0, idx)
          : null;

      if (StringUtils.hasText(tenant) && tenantService.exists(tenant)) {
        TenantHolder.set(tenant);
      }

      chain.doFilter(req, res);
    } finally {
      TenantHolder.unset();
    }
  }
}

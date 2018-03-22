package com.netradius.spring.tenancy.hibernate;

import com.netradius.spring.tenancy.core.TenantHolder;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.util.StringUtils;

/**
 * Used to resolve the tenant for the executing context.
 *
 * @author Erik R. Jensen
 */
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

  @Override
  public String resolveCurrentTenantIdentifier() {
    String tenant = TenantHolder.get();
    return StringUtils.hasText(tenant) ? tenant : "public";
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}

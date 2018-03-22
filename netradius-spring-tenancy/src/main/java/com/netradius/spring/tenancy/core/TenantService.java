package com.netradius.spring.tenancy.core;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Contract for tenant services.
 *
 * @author Erik R. Jensen
 */
public interface TenantService {

  boolean exists(@Nullable String tenant);

  boolean create(@Nonnull String tenant);

  boolean drop(@Nonnull String tenant);

  boolean rename(@Nonnull String tenant, @Nonnull String newTenant);

  int count();

  @Nonnull
  List<String> list();

}

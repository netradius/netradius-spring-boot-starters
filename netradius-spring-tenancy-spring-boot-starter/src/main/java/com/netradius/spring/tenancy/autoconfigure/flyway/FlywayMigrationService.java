package com.netradius.spring.tenancy.autoconfigure.flyway;

import javax.annotation.Nonnull;

/**
 * Contract for tenant aware migration services.
 *
 * @author Erik R. Jensen
 */
public interface FlywayMigrationService {

  void migrate();

  void migrate(@Nonnull String tenant);

}

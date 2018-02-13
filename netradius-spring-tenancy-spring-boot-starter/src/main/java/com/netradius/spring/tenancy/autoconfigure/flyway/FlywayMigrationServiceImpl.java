package com.netradius.spring.tenancy.autoconfigure.flyway;

import com.netradius.spring.tenancy.core.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

/**
 * Tenant aware migration service implementation.
 *
 * @author Erik R. Jensen
 */
@Service
@Slf4j
public class FlywayMigrationServiceImpl implements FlywayMigrationService {

  @Autowired
  private Flyway flyway;

  @Autowired
  private TenantService tenantService;

  @Autowired
  private FlywayMigrationStrategy flywayMigrationStrategy;

  @Override
  @PostConstruct
  public void migrate() {
    List<String> tenants = tenantService.list();
    tenants.forEach(this::migrate);
  }

  @Override
  public void migrate(@Nonnull String tenant) {
    if (tenantService.exists(tenant)) {
      log.debug("Applying migrations to tenant " + tenant);
      synchronized (this) { // We can't have threads stepping on one another
        try {
          flyway.setSchemas(tenant);
          flywayMigrationStrategy.migrate(flyway);
        } finally {
          flyway.setSchemas("public");
        }
      }
    }
  }
}

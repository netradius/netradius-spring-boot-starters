package com.netradius.spring.tenancy.autoconfigure;

import com.netradius.spring.tenancy.autoconfigure.flyway.FlywayMigrationService;
import com.netradius.spring.tenancy.autoconfigure.flyway.FlywayMigrationServiceImpl;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value = org.flywaydb.core.Flyway.class)
@ConditionalOnBean(value = javax.sql.DataSource.class)
@AutoConfigureAfter(FlywayAutoConfiguration.class)
public class SpringFlywayMigrationAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(value = FlywayMigrationStrategy.class)
  public FlywayMigrationStrategy flywayMigrationStrategy() {
    return (Flyway::migrate);
  }

  @Bean
  public FlywayMigrationService migrationService() {
    return new FlywayMigrationServiceImpl();
  }

}

package com.netradius.spring.tenancy.core;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Integration tests for TenantServiceImpl.
 *
 * @author Erik R. Jensen
 */
@Slf4j
public class PostgresTenantServiceTest {

  private TenantService tenantService;

  @Before
  public void setup() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:postgresql://127.0.0.1:54320/netradius");
    config.setUsername("netradius");
    config.setPassword("Niproof1#difShuj3");
    HikariDataSource dataSource = new HikariDataSource(config);
    tenantService = new PostgresTenantService(dataSource);
  }

  @Test
  public void test() {
    log.debug("Testing tenant services");
    if (tenantService.exists("integration")) {
      tenantService.drop("integration");
    }
    if (tenantService.exists("yourmom")) {
      tenantService.drop("yourmom");
    }
    int count = tenantService.count();
    assertTrue(tenantService.create("integration"));
    log.info(tenantService.count() + " tenants found: " + tenantService.list());
    assertThat(tenantService.count(), greaterThanOrEqualTo(1));
    assertTrue(tenantService.exists("integration"));
    assertTrue(tenantService.rename("integration", "yourmom"));
    assertTrue(tenantService.drop("yourmom"));
    assertThat(count, equalTo(tenantService.count()));
  }

  @Test
  public void testExistsDisallowed1() {
    assertThat(tenantService.exists("public"), equalTo(false));
  }

  @Test
  public void testExistsDisallowed2() {
    assertThat(tenantService.exists("information_schema"), equalTo(false));
  }

  @Test
  public void testExistsDisallowed3() {
    assertThat(tenantService.exists("pg_catalog"), equalTo(false));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddDisallowed1() {
    tenantService.create("public");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddDisallowed2() {
    tenantService.create("information_schema");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddDisallowed3() {
    tenantService.create("pg_catalog");
  }
}

package com.netradius.spring.tenancy.core;

import com.netradius.commons.lang.ValidationHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;

/**
 * Implementation of tenant services for PostgreSQL.
 *
 * @author Erik R. Jensen
 */
@Slf4j
public class PostgresTenantService implements TenantService {

  protected static final String TENANT_REGEX = "[a-z][a-z0-9]+";

  protected static final String EXISTS_QUERY =
      "SELECT 1 FROM information_schema.schemata "
          + "WHERE schema_name = lower(?)";

  protected static final String LIST_QUERY =
      "SELECT schema_name FROM information_schema.schemata "
          + "WHERE schema_name NOT IN ('information_schema', 'pg_catalog') "
          + "ORDER BY schema_name";

  protected static final String COUNT_QUERY =
      "SELECT count(1) FROM information_schema.schemata "
          + "WHERE schema_name NOT IN ('information_schema', 'pg_catalog')";



  protected Set<String> disallowedTenants = new HashSet<String>() {
    {
      add("pg_catalog");
      add("information_schema");
      add("public");
    }
  };

  protected DataSource dataSource;

  public PostgresTenantService(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  protected void checkAllowed(String tenant) {
    if (StringUtils.hasText(tenant)) {
      tenant = tenant.toLowerCase();
      if (disallowedTenants.contains(tenant)) {
        throw new IllegalArgumentException("Tenant [" + tenant + "] is not allowed");
      }
    }
  }

  @Override
  public boolean exists(@Nullable String tenant) {
    if (StringUtils.hasText(tenant) && !disallowedTenants.contains(tenant)) {
      try (Connection c = dataSource.getConnection();
          PreparedStatement ps = c.prepareStatement(EXISTS_QUERY)) {
        ps.setString(1, tenant);
        try (ResultSet rs = ps.executeQuery()) {
          return rs.next();
        }
      } catch (SQLException x) {
        log.error("Error querying for schemas: " + x.getMessage(), x);
      }
    }
    return false;
  }

  @Override
  public boolean create(@Nonnull String tenant) {
    ValidationHelper.checkForEmpty(tenant);
    tenant = tenant.toLowerCase();
    checkAllowed(tenant);
    ValidationHelper.checkRegex(tenant, TENANT_REGEX);
    if (exists(tenant)) {
      return false;
    }
    try (Connection c = dataSource.getConnection();
        Statement stmt = c.createStatement()) {
      stmt.execute("CREATE SCHEMA " + tenant);
      return true;
    } catch (SQLException x) {
      log.error("Error creating schema [" + tenant + "]: " + x.getMessage(), x);
    }
    return false;
  }

  @Override
  public boolean drop(@Nonnull String tenant) {
    ValidationHelper.checkForEmpty(tenant);
    tenant = tenant.toLowerCase();
    checkAllowed(tenant);
    ValidationHelper.checkRegex(tenant, TENANT_REGEX);
    if (!exists(tenant)) {
      return false;
    }
    try (Connection c = dataSource.getConnection();
        Statement stmt = c.createStatement()) {
      stmt.executeUpdate("DROP SCHEMA " + tenant + " CASCADE");
      return true;
    } catch (SQLException x) {
      log.error("Error drop schema: [" + tenant + "]: " + x.getMessage(), x);
    }
    return false;
  }

  @Override
  public boolean rename(@Nonnull String tenant, @Nonnull String newTenant) {
    ValidationHelper.checkForEmpty(tenant);
    tenant = tenant.toLowerCase();
    ValidationHelper.checkRegex(tenant, TENANT_REGEX);
    ValidationHelper.checkForEmpty(newTenant);
    newTenant = newTenant.toLowerCase();
    ValidationHelper.checkRegex(newTenant, TENANT_REGEX);
    checkAllowed(tenant);
    checkAllowed(newTenant);
    if (exists(newTenant)) {
      return false;
    }
    try (Connection c = dataSource.getConnection();
        Statement stmt = c.createStatement()) {
      stmt.execute("ALTER SCHEMA " + tenant + " RENAME TO " + newTenant);
      return true;
    } catch (SQLException x) {
      log.error("Error renaming schema [" + tenant + "] to [" + newTenant + "]: " + x.getMessage(),
          x);
    }
    return false;
  }

  @Override
  public int count() {
    try (Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement(COUNT_QUERY);
        ResultSet rs = ps.executeQuery()) {
      if (rs.next()) {
        return rs.getInt(1);
      }
    } catch (SQLException x) {
      log.error("Error counting tenants: " + x.getMessage(), x);
    }
    return 0;
  }

  @Nonnull
  @Override
  public List<String> list() {
    List<String> tenants = new ArrayList<>(count());
    try (Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement(LIST_QUERY);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        String tenant = rs.getString(1);
        if (!tenant.startsWith("pg_")) {
          tenants.add(tenant);
        }
      }
    } catch (SQLException x) {
      log.error("Error querying tenant: " + x.getMessage(), x);
    }
    return tenants;
  }
}

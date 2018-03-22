package com.netradius.spring.tenancy.hibernate;

import com.netradius.spring.tenancy.core.SpringHelper;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.context.ApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * A simple connection provider which uses an existing data source and supports
 * SCHEMA based multi-tenancy with PostgreSQL.
 *
 * @author Erik R. Jensen
 */
public class TenantConnectionProvider implements MultiTenantConnectionProvider {

  private DataSource dataSource;

  private Connection getConnectionInternal() throws SQLException {
    if (dataSource == null) {
      dataSource = SpringHelper.getBean(DataSource.class);
    }
    assert dataSource != null;
    return dataSource.getConnection();
  }

  @Override
  public Connection getAnyConnection() throws SQLException {
    return getConnection("public");
  }

  @Override
  public void releaseAnyConnection(Connection connection) throws SQLException {
    try {
      connection.createStatement().execute("SET SCHEMA 'public'");
    } catch (Exception x) {
      throw new HibernateException("Failed to reset schema to 'public': " + x.getMessage(), x);
    }
    connection.close();
  }

  @Override
  public Connection getConnection(String s) throws SQLException {
    Connection connection = getConnectionInternal();
    try {
      connection.createStatement().execute("SET SCHEMA '" + s + "'");
    } catch (Exception x) {
      throw new HibernateException("Failed to set schema to '" + s + "': " + x.getMessage(), x);
    }
    return connection;
  }

  @Override
  public void releaseConnection(String s, Connection connection) throws SQLException {
    releaseAnyConnection(connection);
  }

  @Override
  public boolean supportsAggressiveRelease() {
    return false;
  }

  @Override
  public boolean isUnwrappableAs(Class clazz) {
    return ConnectionProvider.class.equals(clazz)
        || TenantConnectionProvider.class.isAssignableFrom(clazz);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T unwrap(Class<T> clazz) {
    if (isUnwrappableAs(clazz)) {
      return (T) this;
    } else {
      throw new UnknownUnwrapTypeException(clazz);
    }
  }
}

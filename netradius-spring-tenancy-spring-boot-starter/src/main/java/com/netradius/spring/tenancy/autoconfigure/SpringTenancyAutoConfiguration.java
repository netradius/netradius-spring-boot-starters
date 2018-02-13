package com.netradius.spring.tenancy.autoconfigure;

import com.netradius.spring.tenancy.core.PostgresTenantService;
import com.netradius.spring.tenancy.core.RootTenantAspect;
import com.netradius.spring.tenancy.core.TenantFilter;
import com.netradius.spring.tenancy.core.TenantService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnBean(value = javax.sql.DataSource.class)
@ServletComponentScan({"com.netradius.spring.tenancy"})
public class SpringTenancyAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(value = RootTenantAspect.class, search = SearchStrategy.CURRENT)
  public RootTenantAspect rootTenantAspect() {
    return new RootTenantAspect();
  }

  @Bean
  @ConditionalOnMissingBean(value = TenantService.class, search = SearchStrategy.CURRENT)
  public TenantService tenantService(DataSource dataSource) {
    return new PostgresTenantService(dataSource);
  }

  @Bean
  @ConditionalOnMissingBean(value = TenantFilter.class, search = SearchStrategy.CURRENT)
  public TenantFilter tenantFilter(TenantService tenantService) {
    return new TenantFilter(tenantService);
  }
}

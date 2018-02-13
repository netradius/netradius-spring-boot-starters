package com.netradius.spring.tenancy.sample;

import com.netradius.spring.tenancy.autoconfigure.flyway.FlywayMigrationService;
import com.netradius.spring.tenancy.core.TenantHolder;
import com.netradius.spring.tenancy.core.TenantService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TenantController {

  @Autowired
  private TenantService tenantService;

  @Autowired
  private FlywayMigrationService flywayMigrationService;

  @RequestMapping(value = "/tenants", method = RequestMethod.GET)
  public List<String> getTenants() {
    return tenantService.list();
  }

  @RequestMapping(value = "/tenants", method = RequestMethod.POST)
  public List<String> addTenant(@RequestParam String tenant) {
    if (!tenantService.exists(tenant)) {
      tenantService.create(tenant);
      flywayMigrationService.migrate(tenant);
    }
    return getTenants();
  }

  @RequestMapping(value = "/tenants", method = RequestMethod.DELETE)
  public void deleteTenant(@RequestParam String tenant) {
    if (tenantService.exists(tenant)) {
      tenantService.drop(tenant);
    }
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class MyTenantResponse {
    private String tenant;
  }

  @RequestMapping(value = "/mytenant", method = RequestMethod.GET)
  public MyTenantResponse getTenant() {
    return new MyTenantResponse(TenantHolder.get());
  }

}

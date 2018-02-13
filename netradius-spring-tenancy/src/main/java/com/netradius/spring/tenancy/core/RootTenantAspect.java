package com.netradius.spring.tenancy.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Aspect used to change execution to the root tenant.
 *
 * @author Erik R. Jensen
 */
@Aspect
public class RootTenantAspect {

  @Around("execution(@com.netradius.spring.tenancy.core.RootTenant * *(..))")
  public Object rootTenant(ProceedingJoinPoint joinPoint) throws Throwable {
    String originalTenant = TenantHolder.get();
    TenantHolder.set("public");
    try {
      return joinPoint.proceed();
    } finally {
      if (originalTenant == null) {
        TenantHolder.unset();
      } else {
        TenantHolder.set(originalTenant);
      }
    }
  }
}

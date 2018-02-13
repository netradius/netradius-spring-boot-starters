package com.netradius.spring.tenancy.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// http://www.stevenlanders.com/2014/07/02/spring-aop-annotation.html

/**
 * Used to set the tenant manually. This in combination with @Async is used to force certain things
 * to always be called on the "public" tenant. This is needed as a workaround to
 * https://hibernate.atlassian.net/browse/HHH-9766
 *
 * @author Erik R. Jensen
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RootTenant {

}

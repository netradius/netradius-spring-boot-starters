package com.netradius.spring.tenancy.sample;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ActorRepository extends PagingAndSortingRepository<Actor, UUID> {

  Actor findByName(String name);

}

package com.netradius.spring.tenancy.sample;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "actor")
public class Actor {

  @Id
  @Column(name = "id", nullable = false, unique = true)
  @Type(type = "pg-uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @Column(name = "name", length = 75, nullable = false)
  private String name;

}

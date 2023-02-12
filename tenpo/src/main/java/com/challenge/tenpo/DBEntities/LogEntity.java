package com.challenge.tenpo.DBEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "audit_log")
public class LogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "uri")
  private String endpoint;
  @Column(name = "httpStatus")
  private Integer httpStatus;
  @Column(name = "response", columnDefinition = "TEXT")
  private String response;
  @Column(name = "dateCreated")
  private LocalDateTime dateCreated;
}

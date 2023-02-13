package com.challenge.tenpo.DBEntities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
  @Column(name = "requestBody", columnDefinition = "TEXT")
  private String requestBody;
  @Column(name = "responseBody", columnDefinition = "TEXT")
  private String responseBody;
  @Column(name = "dateCreated")
  private LocalDateTime dateCreated;
}

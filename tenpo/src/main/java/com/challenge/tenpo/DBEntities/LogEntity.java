package com.challenge.tenpo.DBEntities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "audits")
public class LogEntity {

  public LogEntity() {

  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "uri")
  private String endpoint;
  @Column(name = "httpStatus")
  private Integer httpStatus;
  @Column(name = "responseBody", columnDefinition = "TEXT")
  private String responseBody;
  @Column(name = "errorMessage", columnDefinition = "TEXT")
  private String errorMessage;
  @Column(name = "dateCreated")
  private LocalDateTime dateCreated;
}

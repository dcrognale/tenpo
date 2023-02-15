package com.challenge.tenpo.repositories;

import com.challenge.tenpo.DBEntities.LogEntity;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public interface LogRepository extends CrudRepository<LogEntity, Integer> {

  @Query("SELECT LOG FROM LogEntity LOG " +
      "WHERE (LOG.dateCreated BETWEEN :from AND :to) ORDER BY LOG.dateCreated DESC")
  Page<LogEntity> getHistory(LocalDateTime from, LocalDateTime to, Pageable page);
}

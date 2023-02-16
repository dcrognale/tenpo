package com.challenge.tenpo.repositories;

import com.challenge.tenpo.DBEntities.LogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<LogEntity, Integer> {

  @Query("SELECT LOG FROM LogEntity LOG ORDER BY LOG.dateCreated DESC")
  Page<LogEntity> getHistory(Pageable page);

}

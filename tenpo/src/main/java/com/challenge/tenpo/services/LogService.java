package com.challenge.tenpo.services;

import com.challenge.tenpo.DBEntities.LogEntity;
import com.challenge.tenpo.DBEntities.SearchResult;
import com.challenge.tenpo.repositories.LogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LogService {

  private static final Integer PAGE_SIZE = 10;

  @Autowired
  LogRepository logRepository;

  public void logRequest(HttpServletResponse resp, HttpServletRequest req, Object body) throws JsonProcessingException {

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = ow.writeValueAsString(body);
    LogEntity.builder()
        .dateCreated(LocalDateTime.now())
        .endpoint(req.getRequestURI())
        .httpStatus(resp.getStatus())
        .response(json)
        .build();
  }

  public SearchResult getHistory(LocalDateTime from, LocalDateTime to, Integer page) {

    if (Optional.ofNullable(from).isEmpty() || Optional.ofNullable(to).isEmpty()) {
      throw new IllegalArgumentException("From and To values are mandatories.");
    }

    Pageable pageable = PageRequest.of(page, PAGE_SIZE);
    Page<LogEntity> pageEntity = logRepository.getHistory(from, to, pageable);
    return new SearchResult(pageEntity.getTotalPages(), pageEntity.getTotalElements(),
        pageEntity.getContent().size(), pageEntity.getContent());
  }
}

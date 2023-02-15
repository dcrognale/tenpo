package com.challenge.tenpo.services;

import com.challenge.tenpo.DBEntities.LogEntity;
import com.challenge.tenpo.DBEntities.SearchResult;
import com.challenge.tenpo.repositories.LogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

  public void logRequest(HttpServletResponse resp, HttpServletRequest req,
                         Object responseBody,
                         Object requestBody) throws JsonProcessingException {

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String reqJson = ow.writeValueAsString(requestBody);
    String respJson = ow.writeValueAsString(responseBody);

    LogEntity entity = LogEntity.builder()
        .dateCreated(LocalDateTime.now())
        .endpoint(req.getRequestURI())
        .httpStatus(resp.getStatus())
        .responseBody(respJson)
        .requestBody(reqJson)
        .build();

    logRepository.save(entity);
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

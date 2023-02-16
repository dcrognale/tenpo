package com.challenge.tenpo.services;

import com.challenge.tenpo.DBEntities.LogEntity;
import com.challenge.tenpo.DBEntities.SearchResult;
import com.challenge.tenpo.repositories.LogRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

@Service
public class LogService {

  private static final Integer PAGE_SIZE = 5;

  @Autowired
  LogRepository logRepository;

  public void logRequest(ContentCachingResponseWrapper resp, ContentCachingRequestWrapper req) throws IOException {

    String errorMessage = null;

    if (HttpStatus.valueOf(resp.getStatus()).is4xxClientError()) {
      errorMessage = HttpStatus.valueOf(resp.getStatus()).getReasonPhrase();
    }

    LogEntity entity = new LogEntity();
    entity.setDateCreated(LocalDateTime.now());
    entity.setEndpoint(req.getRequestURI());
    entity.setHttpStatus(resp.getStatus());
    entity.setResponseBody(getResponseBody(resp));
    entity.setErrorMessage(errorMessage);

    save(entity);
  }

  @Async
  void save(LogEntity entity) {
    logRepository.save(entity);
  }

  private String getResponseBody(final HttpServletResponse response) throws IOException {
    String payload = null;
    ContentCachingResponseWrapper wrapper =
        WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
    if (wrapper != null) {
      byte[] buf = wrapper.getContentAsByteArray();
      if (buf.length > 0) {
        payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
        wrapper.copyBodyToResponse();
      }
    }
    return null == payload ? " - " : payload;
  }

  public SearchResult getHistory(Integer page) {

    if (Optional.ofNullable(page).isEmpty()) {
      throw new IllegalArgumentException("Page value is mandatory");
    }

    Pageable pageable = PageRequest.of(page, PAGE_SIZE);
    Page<LogEntity> pageEntity = logRepository.getHistory(pageable);
    return new SearchResult(pageEntity.getTotalPages(), pageEntity.getTotalElements(),
        pageEntity.getContent().size(), pageEntity.getContent());
  }
}

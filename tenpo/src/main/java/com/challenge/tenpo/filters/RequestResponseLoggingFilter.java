package com.challenge.tenpo.filters;

import com.challenge.tenpo.services.LogService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Order(1)
@Slf4j
public class RequestResponseLoggingFilter implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

  @Autowired
  LogService logService;

  @Override
  public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {

    ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
    ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper((HttpServletResponse) response);

    filterChain.doFilter(req, resp);

    byte[] responseBody = resp.getContentAsByteArray();
    byte[] requestBody = req.getContentAsByteArray();

    logService.logRequest(resp, req, responseBody, requestBody);

    LOGGER.info("Tenpo: Request body = {}", new String(requestBody, StandardCharsets.UTF_8));

    LOGGER.info("Tenpo: Response body = {}", new String(responseBody, StandardCharsets.UTF_8));

    resp.copyBodyToResponse();
  }
}

package com.challenge.tenpo.services;

import com.challenge.tenpo.dtos.PercentageDTO;
import com.challenge.tenpo.exceptions.RetryException;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@EnableScheduling
public class AdderService {

  private final RestTemplate restTemplate;
  private static final String VALIDATION_PERCENTAGE_ERROR = "Percentage is not possible to calculate";

  @Value("${BASE_PATH}")
  private String basePath;

  @Value("${PERCENTAGE_PATH}")
  private String percentagePath;

  private final int DELAY = 30 * 60 * 1000;

  public Double percentaje = null;

  public AdderService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public Double sumValues(Double value1, Double value2) {
    validatePercentage();
    return (value1 + value2) * percentaje;
  }

  private void validatePercentage() {
    Optional.ofNullable(percentaje)
        .orElseThrow(() -> new HttpServerErrorException
            (HttpStatus.INTERNAL_SERVER_ERROR, VALIDATION_PERCENTAGE_ERROR));
  }

  @PostConstruct
  public void onStartup() throws RetryException {
    getPercentage();
  }

  @Scheduled(fixedRate = DELAY)
  public void onSchedule() throws RetryException {
    getPercentage();
  }

  private void getPercentage() throws RetryException {
    PercentageDTO percentageDTO = callApiWithRetries();
    this.percentaje = (percentageDTO.getValue() / 100) + 1;
    System.out.println("Current percentage: " + percentaje);
  }

  @Retryable(value = {RetryException.class}, maxAttempts = 3, backoff = @Backoff(delay = 5000))
  private PercentageDTO callApiWithRetries() throws RetryException {
    ResponseEntity<PercentageDTO> response = null;

    try {
      var headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      var requestEntity = new HttpEntity(null, headers);
      var url = String.format("%s%s", basePath, percentagePath);

      response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, PercentageDTO.class);

    } catch (HttpClientErrorException e) {
      if (e.getStatusCode().is5xxServerError()) {
        throw new RetryException(e.getMessage());
      }
    }

    return response.getBody();
  }
}

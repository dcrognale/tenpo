package com.challenge.tenpo.controllers;

import com.challenge.tenpo.services.AdderService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationController {
  public OperationController() {
    Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));
    this.bucket = Bucket4j.builder()
        .addLimit(limit)
        .build();
  }

  private final Bucket bucket;

  @Autowired
  AdderService adderService;

  @GetMapping("/ping")
  @ResponseBody
  private ResponseEntity<String> ping() {
    return ResponseEntity.ok("pong");
  }

  @GetMapping("/operation")
  @ResponseBody
  private ResponseEntity<Double> sumValues(@NonNull @RequestParam("value1") Double value1,
                                           @NonNull @RequestParam("value2") Double value2) {

    if (bucket.tryConsume(1)) {
      if (value1 < 0 || value2 < 0) {
        throw new IllegalArgumentException("Value must be positive.");
      }

      var result = adderService.sumValues(value1, value2);

      return ResponseEntity.ok(result);
    }

    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
  }
}

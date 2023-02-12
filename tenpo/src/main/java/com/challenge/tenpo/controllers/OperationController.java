package com.challenge.tenpo.controllers;

import com.challenge.tenpo.services.AdderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationController {

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

    if (value1 < 0 || value2 < 0) {
      throw new IllegalArgumentException("Value must be positive.");
    }

    var result = adderService.sumValues(value1, value2);

    return ResponseEntity.ok(result);
  }
}

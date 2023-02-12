package com.external.percentageAPI.controllers;

import com.external.percentageAPI.dtos.PercentageDTO;
import com.github.javafaker.Faker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/percentage")
public class PercentageController {

  private static final Faker FAKER = Faker.instance();

  @RequestMapping(value = "/available", method = RequestMethod.GET)
  public ResponseEntity<PercentageDTO> getAvailablePercentage() {

    var percentage = PercentageDTO.builder()
        .value(FAKER.number().randomDouble(0, 1, 100))
        .build();

    return ResponseEntity.ok(percentage);
  }
}

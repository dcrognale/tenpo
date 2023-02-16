package com.challenge.tenpo.controllers;

import com.challenge.tenpo.DBEntities.SearchResult;
import com.challenge.tenpo.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

  @Autowired
  LogService logService;

  @GetMapping("/logs")
  @ResponseBody
  private ResponseEntity<?> getHistory(@RequestParam("page") Integer page) {

    SearchResult searchResult = logService.getHistory(page);

    return ResponseEntity.ok(searchResult);
  }
}

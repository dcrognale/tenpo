package com.challenge.tenpo.controllers;

import com.challenge.tenpo.DBEntities.SearchResult;
import com.challenge.tenpo.services.LogService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/audit")
public class LogController {

  @Autowired
  LogService logService;

  @GetMapping("/logs")
  @ResponseBody
  private ResponseEntity<?> getHistory(@Nullable @RequestParam("from")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       LocalDateTime from,
                                       @Nullable @RequestParam("to")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       LocalDateTime to,
                                       @RequestParam("page") Integer page) {

    if (Optional.ofNullable(from).isEmpty() || Optional.ofNullable(to).isEmpty()) {
      throw new IllegalArgumentException("From and To values are mandatories.");
    }

    SearchResult searchResult = logService.getHistory(from, to, page);

    return ResponseEntity.ok(searchResult);
  }
}

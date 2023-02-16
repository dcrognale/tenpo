package com.challenge.tenpo.DBEntities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchResult {
  private Integer pages;
  private Long total;
  private Integer totalResult;
  private List<?> searchResults;
}

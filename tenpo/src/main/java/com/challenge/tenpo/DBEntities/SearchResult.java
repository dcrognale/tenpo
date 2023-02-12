package com.challenge.tenpo.DBEntities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
  private Integer pages;
  private Long total;
  private Integer totalResult;
  private List<?> searchResults;
}

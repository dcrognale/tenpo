package com.challenge.tenpo.exceptions;

public class RetryException extends Exception {
  private String message;

  public RetryException(String message) {
    this.message = message;
  }
}

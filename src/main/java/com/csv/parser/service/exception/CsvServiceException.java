package com.csv.parser.service.exception;

public class CsvServiceException extends RuntimeException {

  public CsvServiceException(String message) {
    super(message);
  }

  public CsvServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}

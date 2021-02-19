package com.csv.parser.web;

import java.text.ParseException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<WebErrorResponse> handleRuntimeException(RuntimeException e) {
    log.debug("{} was thrown", e.getClass(), e);
    return new ResponseEntity<>(new WebErrorResponse(e.getLocalizedMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ParseException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<WebErrorResponse> handleParseException(ParseException e) {
    log.debug("{} was thrown", e.getClass(), e);
    return new ResponseEntity<>(new WebErrorResponse(e.getLocalizedMessage()),
        HttpStatus.BAD_REQUEST);
  }
}
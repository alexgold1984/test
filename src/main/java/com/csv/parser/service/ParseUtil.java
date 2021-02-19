package com.csv.parser.service;

import com.csv.parser.service.exception.CsvServiceException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class ParseUtil {

  public static Date parseString(String date) {
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    try {
      if (date != null && !date.isEmpty()) {
        return dateFormat.parse(date);
      }
      return null;
    } catch (ParseException e) {
      log.error(e);
      throw new CsvServiceException("Error while parse Date", e.getCause());
    }
  }


}

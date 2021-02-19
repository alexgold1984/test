package com.csv.domain;

import com.csv.parser.service.ParseUtil;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomFilterRequest implements Serializable {

  public CustomFilterRequest(String team, String product, String bookingtype,
      String startDate, String endDate) {
    this.team = team;
    this.product = product;
    this.bookingtype = bookingtype;
    this.startDate = ParseUtil.parseString(startDate);
    this.endDate = ParseUtil.parseString(endDate);
  }

  private String team;
  private String product;
  private String bookingtype;
  private Date startDate;
  private Date endDate;
}

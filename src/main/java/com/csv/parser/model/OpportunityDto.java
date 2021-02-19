package com.csv.parser.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OpportunityDto {

  private String id;
  private String customerName;
  private Date bookingDate;
  private String opportunityID;
  private String bookingType;
  private BigDecimal total;
  private String accountExecutive;
  private String salesOrganization;
  private String team;
  private String product;
  private String renewable;
}

package com.csv.parser.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name = "opportunity")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@FilterDef(name = "filterByTeam", parameters = {@ParamDef(name = "team", type = "string")})
@FilterDef(name = "filterByProduct", parameters = {@ParamDef(name = "product", type = "string")})
@FilterDef(name = "filterByBookingType", parameters = {
    @ParamDef(name = "bookingType", type = "string")})
@FilterDef(name = "filterByBookingDate", parameters = {@ParamDef(name = "startDate", type = "date"),
    @ParamDef(name = "endDate", type = "date")})
@Filters({
    @Filter(name = "filterByTeam", condition = ":team = team"),
    @Filter(name = "filterByProduct", condition = ":product = product"),
    @Filter(name = "filterByBookingType", condition = ":booking_type = bookingType"),
    @Filter(name = "filterByBookingDate", condition = "booking_date between :startDate and :endDate"),
})
public class Opportunity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "customer_name", updatable = false, nullable = false)
  private String customerName;

  @Column(name = "booking_date", updatable = false, nullable = false)
  private Date bookingDate;

  @Column(name = "opportunity_id", updatable = false, unique = true, nullable = false)
  private String opportunityID;

  @Column(name = "booking_type", updatable = false, nullable = false)
  private String bookingType;

  @Column(name = "total", updatable = false, nullable = false)
  private BigDecimal total;

  @Column(name = "account_executive", updatable = false, nullable = false)
  private String accountExecutive;

  @Column(name = "sales_organization", updatable = false, nullable = false)
  private String salesOrganization;

  @Column(name = "team", updatable = false, nullable = false)
  private String team;

  @Column(name = "product", updatable = false, nullable = false)
  private String product;

  @Column(name = "renewable", updatable = false, nullable = false)
  private String renewable;
}

package com.csv.parser.persistence.repository.impl;

import com.csv.domain.CustomFilterRequest;
import com.csv.parser.persistence.entity.Opportunity;
import com.csv.parser.persistence.repository.CsvDataRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FilteredCsvDataRepository {

  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private CsvDataRepository dataRepository;

  @Transactional
  public List<Opportunity> findByFilterParams(CustomFilterRequest filter) {
    Session session = entityManager.unwrap(Session.class);
    if (filter.getTeam() != null) {
      session.enableFilter("filterByTeam").setParameter("team", filter.getTeam());
    }
    if (filter.getProduct() != null) {
      session.enableFilter("filterByProduct").setParameter("product", filter.getProduct());
    }
    if (filter.getBookingtype() != null) {
      session.enableFilter("filterByBookingType")
          .setParameter("bookingType", filter.getBookingtype());
    }
    if (filter.getStartDate() != null && filter.getEndDate() != null) {
      session.enableFilter("filterByBookingDate")
          .setParameter("startDate", filter.getStartDate())
          .setParameter("endDate", filter.getEndDate());
    }

    Iterable<Opportunity> iterable = dataRepository.findAll();
    session.disableFilter("filterByTeam");
    session.disableFilter("filterByProduct");
    session.disableFilter("filterByBookingType");
    session.disableFilter("filterByBookingDate");

    List<Opportunity> result = new ArrayList<>();
    iterable.forEach(result::add);
    return result;
  }
}
package com.csv.parser.persistence.repository;

import com.csv.parser.persistence.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvDataRepository extends JpaRepository<Opportunity, Long> {

}

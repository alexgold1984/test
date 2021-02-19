package com.csv.parser.service;

import com.csv.domain.CustomFilterRequest;
import com.csv.parser.model.OpportunityDto;
import com.csv.parser.persistence.entity.FileMetadata;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CsvService {

  void storeCsvDataIntoDb(MultipartFile csvFile);

  void processCsvFile(MultipartFile csvFile);

  List<OpportunityDto> getOpportunities(CustomFilterRequest filter);

  void storeCsvMetadataIntoDb(FileMetadata metadata);
}

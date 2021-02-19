package com.csv.parser.service.impl;

import com.csv.domain.CustomFilterRequest;
import com.csv.parser.mapper.CsvDataMapper;
import com.csv.parser.model.OpportunityDto;
import com.csv.parser.persistence.entity.FileMetadata;
import com.csv.parser.persistence.entity.Opportunity;
import com.csv.parser.persistence.repository.CsvDataRepository;
import com.csv.parser.persistence.repository.FileMetadataRepository;
import com.csv.parser.persistence.repository.impl.FilteredCsvDataRepository;
import com.csv.parser.service.CsvService;
import com.csv.parser.service.ParseUtil;
import com.csv.parser.service.StorageService;
import com.csv.parser.service.exception.CsvServiceException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
public class CsvServiceImpl implements CsvService {

  @Autowired
  private StorageService storageService;
  @Autowired
  private FileMetadataRepository metadataRepository;
  @Autowired
  private CsvDataRepository csvDataRepository;
  @Autowired
  private CsvDataMapper dataMapper;
  @Autowired
  private FilteredCsvDataRepository filteredCsvDataRepository;

  private final String CUSTOMER_NAME = "CustomerName";
  private final String BOOKING_DATE = "BookingDate";
  private final String OPPORTUNITY_ID = "OpportunityID";
  private final String BOOKING_TYPE = "BookingType";
  private final String TOTAL = "Total";
  private final String ACCOUNT_EXECUTIVE = "AccountExecutive";
  private final String SALES_ORGANIZATION = "SalesOrganization";
  private final String TEAM = "Team";
  private final String PRODUCT = "Product";
  private final String RENEWABLE = "Renewable";

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void storeCsvDataIntoDb(MultipartFile csvFile) {
    log.info("Store csv data in database");
    List<Opportunity> csvDataList = parseCsvFile(csvFile);

    Set<Opportunity> csvFileDataSet = csvDataList.stream()
        .collect(Collectors.toCollection(() ->
            new TreeSet<>(Comparator.comparing(Opportunity::getOpportunityID))));

    csvDataRepository.saveAll(csvFileDataSet);
  }

  @Override
  @Transactional
  public void processCsvFile(MultipartFile csvFile) {
    log.info("Process file {}", csvFile.getName());
    FileMetadata metadata = storageService.storeFile(csvFile);
    storeCsvMetadataIntoDb(metadata);
    storeCsvDataIntoDb(csvFile);
  }

  @Override
  @Transactional
  public List<OpportunityDto> getOpportunities(CustomFilterRequest filter) {
    log.info("Get opportunities");
    List<Opportunity> result = filteredCsvDataRepository.findByFilterParams(filter);
    return result.stream()
        .map(dataMapper.INSTANCE::toDto).collect(Collectors.toList());
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void storeCsvMetadataIntoDb(FileMetadata metadata) {
    log.info("Save file metadata to database for file {}", metadata.getName());

    metadataRepository.save(metadata);
  }

  private List<Opportunity> parseCsvFile(MultipartFile csvFile) {
    log.info("Parse csv file {}", csvFile.getOriginalFilename());

    List<Opportunity> records = new ArrayList<>();
    try (BufferedReader fileReader = new BufferedReader(
        new InputStreamReader(csvFile.getInputStream(), "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
        Opportunity opportunity = Opportunity.builder()
            .customerName(csvRecord.get(CUSTOMER_NAME))
            .bookingDate(ParseUtil.parseString(csvRecord.get(BOOKING_DATE)))
            .opportunityID(csvRecord.get(OPPORTUNITY_ID))
            .bookingType(csvRecord.get(BOOKING_TYPE))
            .accountExecutive(csvRecord.get(BOOKING_TYPE))
            .salesOrganization(csvRecord.get(SALES_ORGANIZATION))
            .team(csvRecord.get(TEAM))
            .product(csvRecord.get(PRODUCT))
            .renewable(csvRecord.get(RENEWABLE))
            .total(parseBigDecimal(csvRecord.get(TOTAL)))
            .accountExecutive(csvRecord.get(ACCOUNT_EXECUTIVE))
            .build();
        records.add(opportunity);
      }

    } catch (Exception ex) {
      log.error(ex);
      throw new CsvServiceException("Error while parse file", ex.getCause());
    }
    return records;
  }



  private BigDecimal parseBigDecimal(String value) {
    String formatted = value.replaceAll("[$+^:,]","").trim();
    return new BigDecimal(formatted);
  }
}

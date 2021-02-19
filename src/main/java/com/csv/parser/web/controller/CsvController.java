package com.csv.parser.web.controller;

import com.csv.domain.CustomFilterRequest;
import com.csv.parser.model.OpportunityDto;
import com.csv.parser.service.CsvService;
import com.csv.parser.web.interceptors.MultipartInterceptor;
import com.csv.parser.web.swagger.CsvControllerEndpoint;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Log4j2
public class CsvController implements CsvControllerEndpoint {

  private final CsvService csvService;

  public CsvController(CsvService csvService) {
    this.csvService = csvService;
  }

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> uploadCSV(
      @RequestPart(value = MultipartInterceptor.REQUEST_PART_NAME) MultipartFile file) {
    log.info("Upload file {}", file.getOriginalFilename());
    csvService.processCsvFile(file);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(value = "/opportunity", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<OpportunityDto>> getAll(
      @RequestParam(value = "team", required = false) String team,
      @RequestParam(value = "product", required = false) String product,
      @RequestParam(value = "bookingtype", required = false) String bookingtype,
      @RequestParam(value = "startDate", required = false) String startDate,
      @RequestParam(value = "endDate", required = false) String endDate) {

    log.info("Get opportunities");

    List<OpportunityDto> opportunities = csvService
        .getOpportunities(new CustomFilterRequest(team, product,
            bookingtype, startDate, endDate));
    return new ResponseEntity<>(opportunities, HttpStatus.OK);
  }
}

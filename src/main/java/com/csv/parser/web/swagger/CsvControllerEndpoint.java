package com.csv.parser.web.swagger;

import com.csv.domain.MultiPartRequest;
import com.csv.parser.model.OpportunityDto;
import com.csv.parser.web.WebErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Csv management",
    description = "Operations pertaining to csv data")
public interface CsvControllerEndpoint {

  @Operation(summary = "Upload csv file", description = "Upload csv file")
  @ApiResponse(responseCode = "204", description = "File successfully uploaded", content = @Content())
  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = WebErrorResponse.class)))
  ResponseEntity<Void> uploadCSV(
      @RequestBody(description = "Csv file", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = MultiPartRequest.class))) MultipartFile file)
      throws IOException;

  @Operation(summary = "Get opportunities", description = "Get all opportunities")
  @ApiResponse(responseCode = "200", description = "Successful retrieval list of opportunities.", content = @Content(schema = @Schema(implementation = OpportunityDto.class)))
  @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = WebErrorResponse.class)))
  ResponseEntity<List<OpportunityDto>> getAll(
      @Parameter(in = ParameterIn.QUERY, description = "filter parameter", required = false, style = ParameterStyle.SIMPLE) String team,
      @Parameter(in = ParameterIn.QUERY, description = "filter parameter", required = false, style = ParameterStyle.SIMPLE) String product,
      @Parameter(in = ParameterIn.QUERY, description = "filter parameter", required = false, style = ParameterStyle.SIMPLE) String bookingtype,
      @Parameter(in = ParameterIn.QUERY, description = "filter parameter", required = false, style = ParameterStyle.SIMPLE) String startDate,
      @Parameter(in = ParameterIn.QUERY, description = "filter parameter", required = false, style = ParameterStyle.SIMPLE) String endDate);
}


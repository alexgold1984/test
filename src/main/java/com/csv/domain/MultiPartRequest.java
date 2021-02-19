package com.csv.domain;

import com.csv.parser.web.interceptors.MultipartInterceptor;
import io.swagger.v3.oas.annotations.media.Schema;

public class MultiPartRequest {
  @Schema(type = "string", format = "binary", name = MultipartInterceptor.REQUEST_PART_NAME, description = "Csv file")
  public String file;
}

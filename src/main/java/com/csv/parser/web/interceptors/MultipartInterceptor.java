package com.csv.parser.web.interceptors;

import com.csv.parser.service.exception.ErrorMessages;
import com.csv.parser.web.WebErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
@Log4j2
public class MultipartInterceptor extends HandlerInterceptorAdapter {

  @Value("${parser.file.max-size}")
  private Long maxFileSize;
  @Value("${parser.available-mimeTypes}")
  private List<String> availableMimeTypes;

  public static final String REQUEST_PART_NAME = "file";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    log.info("Inspect file");

    MultipartFile multipartFile = ((StandardMultipartHttpServletRequest) request).getFileMap()
        .get(REQUEST_PART_NAME);

    String actualType = multipartFile.getContentType();

    log.info("File type is {}", actualType);

    if (!availableMimeTypes.contains(actualType)) {
      writeResponseError(response, String
          .format(ErrorMessages.FILE_MIME_TYPE_IS_NOT_SUPPORTED, actualType));
      return false;
    }

    if (multipartFile.isEmpty()) {
      writeResponseError(response, String
          .format(ErrorMessages.EMPTY_FILE));
      return false;
    }

    long size = multipartFile.getSize();

    log.info("File size is {}", size);

    if (size > maxFileSize) {
      writeResponseError(response, String
          .format(ErrorMessages.INVALID_SIZE,
              FileUtils.byteCountToDisplaySize(maxFileSize)));
      return false;
    }

    return true;
  }

  private void writeResponseError(HttpServletResponse response, String errorMessage)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    String jsonStr = new ObjectMapper().writerWithDefaultPrettyPrinter()
        .writeValueAsString(new WebErrorResponse(errorMessage));

    response.getWriter().write(jsonStr);
    response.getWriter().flush();
  }
}

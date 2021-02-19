package com.csv.parser.service.impl;

import com.csv.parser.persistence.entity.FileMetadata;
import com.csv.parser.service.StorageService;
import com.csv.parser.service.exception.StorageException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
public class StorageServiceImpl implements StorageService {

  @Value("${upload.path}")
  private String path;

  public FileMetadata storeFile(MultipartFile csvFile) {
    Date uploadDate = new Date();
    String formattedUploadDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(uploadDate);
    String fileName = csvFile.getOriginalFilename().concat(formattedUploadDate);
    Path uploadPath = Paths.get(path);

    try (InputStream inputStream = csvFile.getInputStream()) {
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
      return buildFileMetada(filePath, csvFile.getSize(), uploadDate, fileName);

    } catch (IOException e) {
      throw new StorageException(String.format("Failed to store file %f", csvFile.getName()), e);
    }
  }

  private FileMetadata buildFileMetada(Path path, Long size, Date uploadDate, String name)
      throws IOException {
    BasicFileAttributes attr = Files.readAttributes(path,
        BasicFileAttributes.class);

    long creationDate = attr.creationTime().toInstant().toEpochMilli();

    return FileMetadata.builder()
        .name(name)
        .size(size)
        .uploadDate(uploadDate)
        .creationDate(new Date(creationDate))
        .build();
  }
}

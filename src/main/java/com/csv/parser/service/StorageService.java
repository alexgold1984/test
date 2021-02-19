package com.csv.parser.service;

import com.csv.parser.persistence.entity.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  FileMetadata storeFile(MultipartFile csvFile);
}

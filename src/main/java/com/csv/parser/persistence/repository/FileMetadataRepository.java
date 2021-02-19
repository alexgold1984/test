package com.csv.parser.persistence.repository;

import com.csv.parser.persistence.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

}

package com.csv.parser.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "csv_metadata")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class FileMetadata implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name", updatable = false, nullable = false)
  private String name;

  @Column(name = "creation_date", updatable = false, nullable = false)
  private Date creationDate;

  @Column(name = "size", updatable = false, nullable = false)
  private Long size;

  @Column(name = "upload_date", nullable = false)
  private Date uploadDate;

  @PrePersist
  public void created() {
    uploadedAt();
  }

  private void uploadedAt() {
    this.uploadDate = new Date();
  }
}

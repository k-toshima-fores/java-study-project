package com.example.demo.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MDivision {
  private Long id;
  private String divisionId;
  private String divisionName;
  private String codeValue;
  private String codeName;
  private Boolean isDeleted;
  private Long createdBy;
  private LocalDateTime createdAt;
  private Long updatedBy;
  private LocalDateTime updatedAt;
}

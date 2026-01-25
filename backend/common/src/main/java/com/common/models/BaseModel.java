package com.common.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseModel {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

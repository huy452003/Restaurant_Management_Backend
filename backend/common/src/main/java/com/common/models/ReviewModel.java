package com.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewModel extends BaseModel{
    private Integer orderId;
    private Integer rating;
    private String comment;
    private String customerName;
}

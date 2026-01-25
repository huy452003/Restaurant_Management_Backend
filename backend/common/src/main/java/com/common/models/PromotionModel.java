package com.common.models;

import com.common.enums.DiscountType;
import com.common.enums.PromotionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionModel extends BaseModel{
    private String name;
    private String decription;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal minOrderValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private PromotionStatus promotionStatus;
}

package com.common.models;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.common.enums.OrderStatus;
import com.common.enums.OrderType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel extends BaseModel{
    private String orderNumber;
    private Integer tableId;
    private Integer waiterId;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private BigDecimal subTotal;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private String notes;
    private LocalDateTime completedAt;
}

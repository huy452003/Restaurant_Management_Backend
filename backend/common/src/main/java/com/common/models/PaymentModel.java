package com.common.models;

import java.math.BigDecimal;

import com.common.enums.PaymentMethod;
import com.common.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel extends BaseModel{
    private Integer orderId;
    private Integer cashierId;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
}

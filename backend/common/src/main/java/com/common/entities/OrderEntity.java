package com.common.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.common.enums.OrderStatus;
import com.common.enums.OrderType;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends BaseEntity {
    @Column(name = "order_number", unique = true, length = 20)
    private String orderNumber;
    @Column(name = "table_id", insertable = false, updatable = false)
    private Integer tableId;
    @Column(name = "waiter_id", insertable = false, updatable = false)
    private Integer waiterId;
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "order_type")
    private OrderType orderType;
    @Column(name = "sub_total", nullable = false)
    private BigDecimal subTotal;
    @Column(name = "tax", nullable = false)
    private BigDecimal tax;
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
    @Column(name = "notes")
    private String notes;
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    // version
    @Version
    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableEntity table;
    @ManyToOne
    @JoinColumn(name = "waiter_id")
    private UserEntity waiter;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<PaymentEntity> payments;
}

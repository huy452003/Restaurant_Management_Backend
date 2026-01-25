package com.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "reviews")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity extends BaseEntity {
    @Column(name = "order_id", insertable = false, updatable = false)
    private Integer orderId;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "comment")
    private String comment;
    @Column(name = "customer_name")
    private String customerName;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}

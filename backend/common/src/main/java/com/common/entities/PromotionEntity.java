package com.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.common.enums.PromotionStatus;
import com.common.enums.DiscountType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.List;

@Entity
@Table(name = "promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor 
public class PromotionEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "discount_type")
    private DiscountType discountType;
    @Column(name = "discount_value")
    private BigDecimal discountValue;
    @Column(name = "min_order_value")
    private BigDecimal minOrderValue;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "status")
    private PromotionStatus status;
    
    @ManyToOne
    @JoinColumn(name = "promotion_menu_items")
    private List<MenuItemEntity> menuItems;
}

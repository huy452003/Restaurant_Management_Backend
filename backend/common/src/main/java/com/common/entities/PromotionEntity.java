package com.common.entities;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.common.enums.PromotionStatus;
import com.common.enums.DiscountType;

import java.util.List;

@Entity
@Table(name = "promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
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

    // version
    @Version
    @Column(name = "version")
    private Long version;
    
    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private List<MenuItemEntity> menuItems;
}

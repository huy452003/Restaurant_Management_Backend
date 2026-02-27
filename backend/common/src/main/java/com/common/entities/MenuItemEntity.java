package com.common.entities;

import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import com.common.enums.MenuItemStatus;

@Entity
@Table(name = "menu_items")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "image")
    private String image;
    @Column(name = "category_id", insertable = false, updatable = false)
    private Integer categoryId;
    @Column(name = "menu_item_status")
    private MenuItemStatus menuItemStatus;
    @Column(name = "preparation_time")
    private Integer preparationTime;

    // version
    @Version
    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private PromotionEntity promotion;
    @OneToMany(mappedBy = "menuItem", fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems;
}

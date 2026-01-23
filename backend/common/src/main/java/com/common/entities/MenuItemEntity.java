package com.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import com.common.enums.MenuItemStatus;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import java.util.List;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "menu_items")
@Data
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
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "status")
    private MenuItemStatus status;
    @Column(name = "preparation_time")
    private Integer preparationTime;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private PromotionEntity promotion;
    @OneToMany(mappedBy = "menuItem", fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems;
}

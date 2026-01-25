package com.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.common.enums.InventoryStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventories")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEntity extends BaseEntity {
    @Column(name = "ingredient_name", unique = true)
    private String ingredientName;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "unit")
    private String unit;
    @Column(name = "min_stock_level")
    private Integer minStockLevel;
    @Column(name = "inventory_status")
    private InventoryStatus inventoryStatus;
    @Column(name = "last_restock_date")
    private LocalDateTime lastRestockDate;
}

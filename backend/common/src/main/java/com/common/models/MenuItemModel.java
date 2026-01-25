package com.common.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

import com.common.enums.MenuItemStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemModel extends BaseModel{
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private Integer categoryId;
    private MenuItemStatus menuItemStatus;
    private Integer preparationTime;
}

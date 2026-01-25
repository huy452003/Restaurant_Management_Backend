package com.common.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.common.enums.MenuItemStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel extends BaseModel{
    private String name;
    private String description;
    private String image;
    private MenuItemStatus menuItemStatus;
}

package com.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.common.enums.MenuItemStatus;
import jakarta.persistence.OneToMany;
import java.util.List;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "status")
    private MenuItemStatus status;

    @OneToMany(mappedBy = "category_menu_items", fetch = FetchType.LAZY)
    private List<MenuItemEntity> menuItems;
}

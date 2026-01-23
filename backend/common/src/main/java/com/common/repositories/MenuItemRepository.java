package com.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.common.entities.MenuItemEntity;
import com.common.enums.MenuItemStatus;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Integer> {
    // Tìm menu items theo status
    List<MenuItemEntity> findByStatus(MenuItemStatus status);
    
    // Tìm menu items available
    List<MenuItemEntity> findByStatusOrderByNameAsc(MenuItemStatus status);
    
    // Tìm menu items theo category
    List<MenuItemEntity> findByCategoryId(Integer categoryId);
    
    // Tìm menu items available theo category
    List<MenuItemEntity> findByCategoryIdAndStatus(Integer categoryId, MenuItemStatus status);
    
    // Kiểm tra xem menu item có tồn tại không
    boolean existsByIdAndStatus(Integer id, MenuItemStatus status);
}

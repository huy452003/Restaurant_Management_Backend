package com.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.common.entities.OrderItemEntity;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {
    // Tìm tất cả items của một order
    List<OrderItemEntity> findByOrderId(Integer orderId);
    
    // Tìm items theo menuItem
    List<OrderItemEntity> findByMenuItemId(Integer menuItemId);
}

package com.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.common.entities.ReviewEntity;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    // Tìm reviews theo order
    List<ReviewEntity> findByOrderId(Integer orderId);
    
    // Tìm review theo order (một order chỉ có 1 review)
    Optional<ReviewEntity> findFirstByOrderId(Integer orderId);
    
    // Tìm reviews theo rating
    List<ReviewEntity> findByRating(Integer rating);
    
    // Tìm reviews có rating >= threshold
    List<ReviewEntity> findByRatingGreaterThanEqual(Integer minRating);
    
    // Tính average rating
    @Query("SELECT AVG(r.rating) FROM ReviewEntity r")
    Double getAverageRating();
    
    // Đếm số reviews theo rating
    @Query("SELECT COUNT(r) FROM ReviewEntity r WHERE r.rating = :rating")
    Long countByRating(@Param("rating") Integer rating);
    
    // Tìm reviews theo customer name
    List<ReviewEntity> findByCustomerName(String customerName);
}

package com.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.common.entities.PromotionEntity;
import com.common.enums.PromotionStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, Integer> {
    // Tìm promotion theo name
    Optional<PromotionEntity> findByName(String name);
    
    // Tìm promotions theo status
    List<PromotionEntity> findByStatus(PromotionStatus status);
    
    // Tìm active promotions (đang diễn ra)
    @Query("SELECT p FROM PromotionEntity p WHERE p.status = PromotionStatus.ACTIVE " +
           "AND p.startDate <= :now AND p.endDate >= :now")
    List<PromotionEntity> findActivePromotions(@Param("now") LocalDateTime now);
    
    // Tìm promotions sắp bắt đầu
    @Query("SELECT p FROM PromotionEntity p WHERE p.status = PromotionStatus.ACTIVE " +
           "AND p.startDate > :now AND p.startDate <= :futureDate")
    List<PromotionEntity> findUpcomingPromotions(
        @Param("now") LocalDateTime now,
        @Param("futureDate") LocalDateTime futureDate
    );
    
    // Tìm promotions đã hết hạn
    @Query("SELECT p FROM PromotionEntity p WHERE p.endDate < :now")
    List<PromotionEntity> findExpiredPromotions(@Param("now") LocalDateTime now);
    
    // Kiểm tra xem promotion có tồn tại không
    boolean existsByName(String name);
}

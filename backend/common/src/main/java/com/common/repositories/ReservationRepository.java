package com.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.common.entities.ReservationEntity;
import com.common.enums.ReservationStatus;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {
    // Tìm reservations theo table
    List<ReservationEntity> findByTableId(Integer tableId);
    
    // Tìm reservations theo status
    List<ReservationEntity> findByReservationStatus(ReservationStatus reservationStatus);
    
    // Tìm reservations theo ngày
    List<ReservationEntity> findByReservationDate(LocalDate date);
    
    // Tìm reservations theo table và ngày (quan trọng để check conflict)
    List<ReservationEntity> findByTableIdAndReservationDate(Integer tableId, LocalDate date);
    
    // Tìm reservations theo customer phone
    List<ReservationEntity> findByCustomerPhone(String phone);
    
    // Tìm reservations theo customer email
    List<ReservationEntity> findByCustomerEmail(String email);
    
    // Tìm reservations trong khoảng thời gian
    @Query("SELECT r FROM ReservationEntity r WHERE r.reservationDate = :date " +
           "AND r.tableId = :tableId " +
           "AND r.reservationStatus != ReservationStatus.CANCELLED")
    List<ReservationEntity> findActiveReservationsByTableAndDate(
        @Param("date") LocalDate date,
        @Param("tableId") Integer tableId
    );
}

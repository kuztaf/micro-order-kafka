package com.foodtruck.inventory_service.repository;

import com.foodtruck.inventory_service.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByProductIdOrderByCreatedAtDesc(Long productId);

    @Query("SELECT sm FROM StockMovement sm WHERE sm.productId = :productId AND sm.createdAt BETWEEN :startDate AND :endDate")
    List<StockMovement> findMovementsByProductAndDateRange(
        @Param("productId") Long productId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
}
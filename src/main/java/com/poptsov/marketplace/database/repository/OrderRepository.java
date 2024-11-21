package com.poptsov.marketplace.database.repository;

import com.poptsov.marketplace.database.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Order o WHERE o.id = :id")
    void deleteOrderById(@Param("id") Integer id);
}

package com.poptsov.marketplace.database.repository;

import com.poptsov.marketplace.database.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Shop s WHERE s.id = :id")
    void deleteShopById(@Param("id") Integer id);

    List<Shop> findByActiveTrue();

    boolean existsByName(String name);

    boolean existsByAddress(String address);
}

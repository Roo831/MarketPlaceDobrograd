package com.poptsov.marketplace.database.repository;

import com.poptsov.marketplace.database.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    List<Shop> findByActiveTrue();
}

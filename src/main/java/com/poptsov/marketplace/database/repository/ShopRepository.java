package com.poptsov.marketplace.database.repository;

import com.poptsov.marketplace.database.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
}

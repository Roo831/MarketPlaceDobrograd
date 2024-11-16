package com.poptsov.marketplace.database.repository;

import com.poptsov.marketplace.database.entity.Banned;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannedRepository extends JpaRepository<Banned, Integer> {

}

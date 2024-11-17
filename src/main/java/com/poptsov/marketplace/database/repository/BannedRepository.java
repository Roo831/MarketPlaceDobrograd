package com.poptsov.marketplace.database.repository;

import com.poptsov.marketplace.database.entity.Banned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedRepository extends JpaRepository<Banned, Integer> {



}

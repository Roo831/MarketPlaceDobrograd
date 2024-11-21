package com.poptsov.marketplace.database.repository;

import com.poptsov.marketplace.database.entity.Banned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BannedRepository extends JpaRepository<Banned, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Banned b WHERE b.id = :id")
    void deleteBannedById(@Param("id") Integer id);


}

package com.poptsov.marketplace.database.repository;


import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.UserReadDto;
import com.poptsov.marketplace.mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> getUserById(Integer id);

}

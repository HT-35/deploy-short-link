package com.example.short_link.module.user.repository;

import com.example.short_link.module.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

   UserEntity findByEmail(String email);

}

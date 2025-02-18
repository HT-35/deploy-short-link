package com.example.short_link.module.OriginalLink.repository;

import com.example.short_link.module.OriginalLink.domain.OriginalLink;
import com.example.short_link.module.user.domain.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OriginalLinkRepository extends JpaRepository<OriginalLink, Long> {

    Optional<List<OriginalLink>> findByOriginalLink(String originalLink);


    List<OriginalLink> findByUUID(String UUID);

    List<OriginalLink> findByUsers(UserEntity users);


    // method 1: Native Query
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM originalLink WHERE UUID = :uuid", nativeQuery = true)
    int deleteAllByUuid(@Param("uuid") String uuid);


//    @Modifying
//    @Query(value = "SELECT FROM originalLink where email = : email", nativeQuery = true)
//    List<OriginalLink> handleGetAllOriginalLink(String email);

    // method 2:
    int deleteByUUID(String uuid);
}

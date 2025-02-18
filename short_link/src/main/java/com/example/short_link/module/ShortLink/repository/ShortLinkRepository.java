package com.example.short_link.module.ShortLink.repository;

import com.example.short_link.module.ShortLink.domain.ShortLink;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShortLinkRepository extends JpaRepository<ShortLink, Long> {

    Optional<ShortLink> findByUrlShort(String url);


    @Modifying
    @Transactional
    @Query("UPDATE ShortLink s SET s.countAccess = s.countAccess + :count WHERE s.urlShort = :urlShort")


    void updateCountAccess(@Param("urlShort") String urlShort, @Param("count") int count);

}

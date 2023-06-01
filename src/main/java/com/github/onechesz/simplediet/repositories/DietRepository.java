package com.github.onechesz.simplediet.repositories;

import com.github.onechesz.simplediet.entities.DietEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DietRepository extends JpaRepository<DietEntity, Integer> {
    @Query(value = "SELECT d FROM DietEntity d WHERE d.title LIKE %:search% AND d.duration BETWEEN :from AND :to")
    List<DietEntity> findByTitleContainsWhereDurationFromTo(@Param(value = "search") String search, @Param(value = "from") String from, @Param(value = "to") String to, Sort sort);

    @Query(value = "SELECT d FROM DietEntity d WHERE d.title LIKE %:search% AND d.duration BETWEEN :from AND :to")
    List<DietEntity> findByTitleContainsWhereDurationFromTo(@Param(value = "search") String search, @Param(value = "from") String from, @Param(value = "to") String to);

    @Query(value = "SELECT d FROM DietEntity d WHERE d.title LIKE %:search% AND d.duration >= :from")
    List<DietEntity> findByTitleContainsWhereDurationFrom(@Param(value = "search") String search, @Param(value = "from") String from, Sort sort);

    @Query(value = "SELECT d FROM DietEntity d WHERE d.title LIKE %:search% AND d.duration >= :from")
    List<DietEntity> findByTitleContainsWhereDurationFrom(@Param(value = "search") String search, @Param(value = "from") String from);

    @Query(value = "SELECT d FROM DietEntity d WHERE d.title LIKE %:search% AND d.duration <= :to")
    List<DietEntity> findByTitleContainsWhereDurationTo(@Param(value = "search") String search, @Param(value = "to") String to, Sort sort);

    @Query(value = "SELECT d FROM DietEntity d WHERE d.title LIKE %:search% AND d.duration <= :to")
    List<DietEntity> findByTitleContainsWhereDurationTo(@Param(value = "search") String search, @Param(value = "to") String to);

    List<DietEntity> findByTitleContains(String search, Sort sort);

    List<DietEntity> findByTitleContains(String search);

    List<DietEntity> findByDurationBetween(String from, String to, Sort sort);

    List<DietEntity> findByDurationBetween(String from, String to);

    List<DietEntity> findByDurationGreaterThan(String from, Sort sort);

    List<DietEntity> findByDurationGreaterThan(String from);

    List<DietEntity> findByDurationLessThan(String from, Sort sort);

    List<DietEntity> findByDurationLessThan(String from);

    @NotNull List<DietEntity> findAll(@NotNull Sort sort);
}

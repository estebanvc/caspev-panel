package com.caspev.panel.repository;

import com.caspev.panel.domain.Door;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Door entity.
 */
@Repository
public interface DoorRepository extends JpaRepository<Door, Long> {

    Optional<Door> findByUuid(String uuid);

    void deleteByUuid(String uuid);
}
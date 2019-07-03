package com.caspev.panel.repository;

import com.caspev.panel.domain.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the EventLog entity.
 */
@Repository
public interface EventLogRepository extends JpaRepository<EventLog, Long> {

    Optional<EventLog> findByUuid(String uuid);

    void deleteByUuid(String uuid);
}
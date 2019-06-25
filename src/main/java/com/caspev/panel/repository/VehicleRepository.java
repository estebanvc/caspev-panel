package com.caspev.panel.repository;

import com.caspev.panel.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vehicle entity.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
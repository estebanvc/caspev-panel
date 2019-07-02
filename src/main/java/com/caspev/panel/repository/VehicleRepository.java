package com.caspev.panel.repository;

import com.caspev.panel.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Vehicle entity.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByUuid(String uuid);

    Optional<Vehicle> findByPlate(String plate);

    void deleteByUuid(String uuid);
}
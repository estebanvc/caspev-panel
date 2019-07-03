package com.caspev.panel.service;

import com.caspev.panel.domain.Vehicle;
import com.caspev.panel.repository.VehicleRepository;
import com.caspev.panel.service.dto.VehicleDTO;
import com.caspev.panel.service.mapper.VehicleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Vehicle.
 */
@Service
@Transactional
public class VehicleService {

    private final Logger            log = LoggerFactory.getLogger(VehicleService.class);
    private final VehicleMapper     vehicleMapper;
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper     = vehicleMapper;
    }

    /**
     * Save a vehicle.
     *
     * @param vehicleDTO the entity to save
     * @return the persisted entity
     */
    public VehicleDTO save(VehicleDTO vehicleDTO) {
        log.debug("Request to save Vehicle : {}", vehicleDTO);
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        vehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDto(vehicle);
    }

    /**
     * Get all the vehicles.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VehicleDTO> findAll() {
        log.debug("Request to get all Vehicles");
        return vehicleRepository.findAll(Sort.by("id").descending())
                .stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one vehicle by uuid.
     *
     * @param uuid the uuid of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<VehicleDTO> findOneByUuid(String uuid) {
        log.debug("Request to get Vehicle : {}", uuid);
        return vehicleRepository.findByUuid(uuid)
                .map(vehicleMapper::toDto);
    }

    /**
     * Get one vehicle by plate.
     *
     * @param plate the plate of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<VehicleDTO> findOneByPlate(String plate) {
        log.debug("Request to get Vehicle : {}", plate);
        return vehicleRepository.findByPlate(plate)
                .map(vehicleMapper::toDto);
    }

    /**
     * Delete the vehicle by uuid.
     *
     * @param uuid the uuid of the entity
     */
    public void delete(String uuid) {
        log.debug("Request to delete Vehicle : {}", uuid);
        vehicleRepository.deleteByUuid(uuid);
    }
}

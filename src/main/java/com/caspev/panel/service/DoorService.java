package com.caspev.panel.service;

import com.caspev.panel.domain.Door;
import com.caspev.panel.repository.DoorRepository;
import com.caspev.panel.service.dto.DoorDTO;
import com.caspev.panel.service.mapper.DoorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Door.
 */
@Service
@Transactional
public class DoorService {

    private final Logger log = LoggerFactory.getLogger(DoorService.class);

    private final DoorRepository doorRepository;

    private final DoorMapper doorMapper;

    public DoorService(DoorRepository doorRepository, DoorMapper doorMapper) {
        this.doorRepository = doorRepository;
        this.doorMapper     = doorMapper;
    }

    /**
     * Save a door.
     *
     * @param doorDTO the entity to save
     * @return the persisted entity
     */
    public DoorDTO save(DoorDTO doorDTO) {
        log.debug("Request to save Door : {}", doorDTO);
        Door door = doorMapper.toEntity(doorDTO);
        door = doorRepository.save(door);
        return doorMapper.toDto(door);
    }

    /**
     * Get all the doors.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DoorDTO> findAll() {
        log.debug("Request to get all Doors");
        return doorRepository.findAll().stream()
                .map(doorMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one door by uuid.
     *
     * @param uuid the uuid of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DoorDTO> findOneByUuid(String uuid) {
        log.debug("Request to get Door : {}", uuid);
        return doorRepository.findByUuid(uuid)
                .map(doorMapper::toDto);
    }

    /**
     * Delete the door by uuid.
     *
     * @param uuid the uuid of the entity
     */
    public void delete(String uuid) {
        log.debug("Request to delete Door : {}", uuid);
        doorRepository.deleteByUuid(uuid);
    }
}

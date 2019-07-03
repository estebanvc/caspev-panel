package com.caspev.panel.service;

import com.caspev.panel.domain.EventLog;
import com.caspev.panel.repository.EventLogRepository;
import com.caspev.panel.service.dto.EventLogDTO;
import com.caspev.panel.service.mapper.EventLogMapper;
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
 * Service Implementation for managing EventLog.
 */
@Service
@Transactional
public class EventLogService {

    private final EventLogMapper     eventLogMapper;
    private final EventLogRepository eventLogRepository;
    private final Logger             log = LoggerFactory.getLogger(EventLogService.class);

    public EventLogService(EventLogRepository eventLogRepository, EventLogMapper eventLogMapper) {
        this.eventLogRepository = eventLogRepository;
        this.eventLogMapper     = eventLogMapper;
    }

    /**
     * Save a eventLog.
     *
     * @param eventLogDTO the entity to save
     * @return the persisted entity
     */
    public EventLogDTO save(EventLogDTO eventLogDTO) {
        log.debug("Request to save EventLog : {}", eventLogDTO);
        EventLog eventLog = eventLogMapper.toEntity(eventLogDTO);
        eventLog = eventLogRepository.save(eventLog);
        return eventLogMapper.toDto(eventLog);
    }

    /**
     * Get all the eventLogs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EventLogDTO> findAll() {
        log.debug("Request to get all EventLogs");
        return eventLogRepository.findAll(Sort.by("id").descending())
                .stream()
                .map(eventLogMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eventLog by uuid.
     *
     * @param uuid the uuid of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EventLogDTO> findOneByUuid(String uuid) {
        log.debug("Request to get EventLog : {}", uuid);
        return eventLogRepository.findByUuid(uuid)
                .map(eventLogMapper::toDto);
    }

    /**
     * Delete the eventLog by uuid.
     *
     * @param uuid the uuid of the entity
     */
    public void delete(String uuid) {
        log.debug("Request to delete EventLog : {}", uuid);
        eventLogRepository.deleteByUuid(uuid);
    }
}

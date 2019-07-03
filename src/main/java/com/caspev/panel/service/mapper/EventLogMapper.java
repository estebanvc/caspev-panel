package com.caspev.panel.service.mapper;

import com.caspev.panel.domain.EventLog;
import com.caspev.panel.service.dto.EventLogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity EventLog and its DTO EventLogDTO.
 */
@Mapper(
        componentModel = "spring",
        uses = {
                VehicleMapper.class,
                NfcCardMapper.class,
                DoorMapper.class,
                UserMapper.class
        })
public interface EventLogMapper extends EntityMapper<EventLogDTO, EventLog> {

    /*@Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "ncfCard.id", target = "ncfCardId")
    @Mapping(source = "door.id", target = "doorId")
    @Mapping(source = "user.id", target = "userId")
    EventLogDTO toDto(EventLog eventLog);

    @Mapping(source = "vehicleId", target = "vehicle")
    @Mapping(source = "ncfCardId", target = "ncfCard")
    @Mapping(source = "doorId", target = "door")
    @Mapping(source = "userId", target = "user")
    EventLog toEntity(EventLogDTO eventLogDTO);*/

    default EventLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventLog eventLog = new EventLog();
        eventLog.setId(id);
        return eventLog;
    }
}

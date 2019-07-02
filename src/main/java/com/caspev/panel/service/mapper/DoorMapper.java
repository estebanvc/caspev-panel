package com.caspev.panel.service.mapper;

import com.caspev.panel.domain.Door;
import com.caspev.panel.service.dto.DoorDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Door and its DTO DoorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DoorMapper extends EntityMapper<DoorDTO, Door> {

    default Door fromId(Long id) {
        if (id == null) {
            return null;
        }
        Door door = new Door();
        door.setId(id);
        return door;
    }
}

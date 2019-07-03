package com.caspev.panel.service.mapper;

import com.caspev.panel.domain.Vehicle;
import com.caspev.panel.service.dto.VehicleEagerDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Vehicle and its DTO VehicleEagerDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface VehicleEagerMapper extends EntityMapper<VehicleEagerDTO, Vehicle> {

    default Vehicle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }
}

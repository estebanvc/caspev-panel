package com.caspev.panel.service.mapper;

import com.caspev.panel.domain.Vehicle;
import com.caspev.panel.service.dto.VehicleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Vehicle and its DTO VehicleDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {

    @Mapping(source = "user.id", target = "userId")
    VehicleDTO toDto(Vehicle vehicle);

    @Mapping(source = "userId", target = "user")
    Vehicle toEntity(VehicleDTO vehicleDTO);

    default Vehicle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }
}

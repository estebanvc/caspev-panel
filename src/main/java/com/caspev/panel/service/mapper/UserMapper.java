package com.caspev.panel.service.mapper;

import com.caspev.panel.domain.User;
import com.caspev.panel.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {


    @Mapping(target = "nfcCards", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    User toEntity(UserDTO userDTO);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User userMod = new User();
        userMod.setId(id);
        return userMod;
    }
}

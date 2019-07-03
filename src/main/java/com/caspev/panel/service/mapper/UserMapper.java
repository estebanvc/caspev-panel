package com.caspev.panel.service.mapper;

import com.caspev.panel.domain.Role;
import com.caspev.panel.domain.User;
import com.caspev.panel.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {


    @Mapping(target = "nfcCards", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    User toEntity(UserDTO userDTO);

    default Set<String> roleSetToStringSet(Set<Role> roleSet) {
        return roleSet.stream().map(Role::getName).collect(Collectors.toSet());
    }

    default Set<Role> roleStringToRoleSet(Set<String> stringSet) {
        return stringSet.stream().map(Role::new).collect(Collectors.toSet());
    }

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User userMod = new User();
        userMod.setId(id);
        return userMod;
    }
}

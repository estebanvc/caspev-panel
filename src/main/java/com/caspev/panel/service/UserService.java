package com.caspev.panel.service;

import com.caspev.panel.domain.Role;
import com.caspev.panel.domain.User;
import com.caspev.panel.repository.RoleRepository;
import com.caspev.panel.repository.UserRepository;
import com.caspev.panel.security.RolesConstants;
import com.caspev.panel.security.SecurityUtils;
import com.caspev.panel.service.dto.UserDTO;
import com.caspev.panel.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing User.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                       UserMapper userMapper, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository  = roleRepository;
        this.userMapper      = userMapper;
        this.userRepository  = userRepository;
    }

    /**
     * Register a user.
     *
     * @param userDTO the entity to save
     * @return the persisted entity
     */
    public UserDTO register(UserDTO userDTO, String password) {
        log.debug("Request to register User : {}", userDTO);
        User user = userMapper.toEntity(userDTO);
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(password));

        Set<Role> roles = new HashSet<>();
        roleRepository.findByName(RolesConstants.ADMIN).ifPresent(roles::add);
        user.setRoles(roles);

        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * Save a user.
     *
     * @param userDTO the entity to save
     * @return the persisted entity
     */
    public UserDTO save(UserDTO userDTO) {
        log.debug("Request to save User : {}", userDTO);
        User user = userMapper.toEntity(userDTO);
        user.setEmail(userDTO.getEmail().toLowerCase());

        Set<Role> roles = new HashSet<>();
        roleRepository.findByName(RolesConstants.USER).ifPresent(roles::add);
        user.setRoles(roles);

        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * Get all the users.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        log.debug("Request to get all Users");
        return userRepository.findAll(Sort.by("id").descending())
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get the current user.
     *
     * @return the current user
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> getCurrentUser() {
        log.debug("Request to get the current User");
        return SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByEmailIgnoreCase)
                .map(userMapper::toDto);
    }

    /**
     * Get one user by uuid.
     *
     * @param uuid the uuid of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> findOneByUuid(String uuid) {
        log.debug("Request to get User : {}", uuid);
        return userRepository.findByUuid(uuid)
                .map(userMapper::toDto);
    }

    /**
     * Get one user by email.
     *
     * @param email the email of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> findOneByEmail(String email) {
        log.debug("Request to get User : {}", email);
        return userRepository.findByEmailIgnoreCase(email)
                .map(userMapper::toDto);
    }

    /**
     * Get one user by rut.
     *
     * @param rut the rut of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> findOneByRut(String rut) {
        log.debug("Request to get User : {}", rut);
        return userRepository.findByRut(rut)
                .map(userMapper::toDto);
    }

    /**
     * Delete the user by uuid.
     *
     * @param uuid the uuid of the entity
     */
    public void delete(String uuid) {
        log.debug("Request to delete User : {}", uuid);
        userRepository.deleteByUuid(uuid);
    }
}
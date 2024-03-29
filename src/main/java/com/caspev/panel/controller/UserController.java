package com.caspev.panel.controller;

import com.caspev.panel.controller.errors.ResourceNotFoundException;
import com.caspev.panel.security.RolesConstants;
import com.caspev.panel.service.NfcCardService;
import com.caspev.panel.service.UserService;
import com.caspev.panel.service.VehicleService;
import com.caspev.panel.service.dto.NfcCardDTO;
import com.caspev.panel.service.dto.UserDTO;
import com.caspev.panel.service.dto.VehicleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Web controller for managing User.
 */
@Secured({RolesConstants.ADMIN})
@Controller
public class UserController {

    private final Logger         log = LoggerFactory.getLogger(UserController.class);
    private final NfcCardService nfcCardService;
    private final UserService    userService;
    private final VehicleService vehicleService;

    public UserController(NfcCardService nfcCardService, UserService userService,
                          VehicleService vehicleService) {
        this.nfcCardService = nfcCardService;
        this.userService    = userService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        log.debug("GET request to get all Users");
        List<UserDTO> userDTOList = userService.findAll();
        model.addAttribute("userDTOList", userDTOList);
        return "user/list";
    }

    @GetMapping("/users/add")
    public String createUser(@ModelAttribute UserDTO userDTO, Model model) {
        model.addAttribute("formAction", "/users/add");
        return "/user/form";
    }

    @PostMapping("/users/add")
    public String createUser(@Valid @ModelAttribute UserDTO userDTO,
                             BindingResult bindingResult, Model model) {
        log.debug("POST request to save User : {}", userDTO);
        model.addAttribute("formAction", "/users/add");
        if (!bindingResult.hasErrors()) {
            if (userService.findOneByEmail(userDTO.getEmail()).isPresent()) {
                bindingResult.addError(
                        new FieldError(
                                "userDTO",
                                "email",
                                "El usuario ya existe"
                        )
                );
            } else if (userService.findOneByRut(userDTO.getRut()).isPresent()) {
                bindingResult.addError(
                        new FieldError(
                                "userDTO",
                                "rut",
                                "El usuario ya existe"
                        )
                );
            } else {
                userDTO.setEmail(userDTO.getEmail().toLowerCase());
                userDTO.addRole(RolesConstants.USER);
                userService.save(userDTO);
                return "redirect:/users";
            }
        }

        return "user/form";
    }

    @GetMapping("/users/{uuid}")
    public String getUser(@PathVariable String uuid, Model model) {
        log.debug("GET request to get User : {}", uuid);
        model.addAttribute("formAction", "/users/" + uuid);
        UserDTO userDTO = userService.findOneByUuid(uuid)
                .orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("userDTO", userDTO);

        List<VehicleDTO> vehicleDTOList = vehicleService.findAllByUserId(userDTO.getId());
        List<NfcCardDTO> nfcCardDTOList = nfcCardService.findAllByUserId(userDTO.getId());

        model.addAttribute("vehicleDTOList", vehicleDTOList);
        model.addAttribute("nfcCardDTOList", nfcCardDTOList);

        return "user/form";
    }

    @PostMapping("/users/{uuid}")
    public String updateUser(@PathVariable String uuid,
                             @Valid @ModelAttribute UserDTO userDTO,
                             BindingResult bindingResult, Model model) {
        log.debug("POST request to update User : {}", userDTO);
        model.addAttribute("formAction", "/users/" + uuid);

        if (!bindingResult.hasErrors()) {
            Long userId = userService.findOneByUuid(uuid)
                    .orElseThrow(ResourceNotFoundException::new).getId();
            userDTO.setId(userId);
            userService.save(userDTO);
            model.addAttribute("recordUpdated", true);

            List<VehicleDTO> vehicleDTOList = vehicleService.findAllByUserId(userDTO.getId());
            List<NfcCardDTO> nfcCardDTOList = nfcCardService.findAllByUserId(userDTO.getId());

            model.addAttribute("vehicleDTOList", vehicleDTOList);
            model.addAttribute("nfcCardDTOList", nfcCardDTOList);
        }

        return "user/form";
    }

    @GetMapping("/users/{uuid}/delete")
    public String deleteUser(@PathVariable String uuid) {
        log.debug("GET request to delete User : {}", uuid);
        userService.findOneByUuid(uuid)
                .ifPresent(userDTO -> userService.delete(userDTO.getUuid()));
        return "redirect:/users";
    }
}
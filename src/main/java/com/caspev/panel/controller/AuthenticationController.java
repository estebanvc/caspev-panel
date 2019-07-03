package com.caspev.panel.controller;

import com.caspev.panel.controller.model.SignUpUserModel;
import com.caspev.panel.security.RolesConstants;
import com.caspev.panel.service.UserService;
import com.caspev.panel.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * Web controller for managing user authentication.
 */
@Controller
public class AuthenticationController {

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Authenticate a user.
     *
     * @return the login view
     */
    @GetMapping("/login")
    public String login() {
        return "authentication/login";
    }

    /**
     * Show the sign up view.
     *
     * @return the sign up view
     */
    @GetMapping("/signup")
    public String signup(@ModelAttribute SignUpUserModel signUpUserModel) {
        return "authentication/signup";
    }

    /**
     * Register a new user.
     *
     * @return the sign up view
     */
    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignUpUserModel signUpUserModel,
                         BindingResult bindingResult) {
        log.debug("POST request to register User : {}", signUpUserModel);
        if (!bindingResult.hasErrors()) {
            if (userService.findOneByEmail(signUpUserModel.getEmail()).isPresent()) {
                bindingResult.addError(
                        new FieldError(
                                "signUpUserModel",
                                "email",
                                "El usuario ya existe"
                        )
                );
            } else {
                UserDTO userDTO = new UserDTO();
                userDTO.setRut(signUpUserModel.getRut());
                userDTO.setEmail(signUpUserModel.getEmail());
                userDTO.setFirstName(signUpUserModel.getFirstName());
                userDTO.setLastName(signUpUserModel.getLastName());
                userDTO.setLevelAccess(0);
                userDTO.addRole(RolesConstants.ADMIN);
                userService.register(userDTO, signUpUserModel.getPassword());
                return "redirect:/vehicles";
            }

        }

        return "authentication/signup";
    }
}

package com.caspev.panel.controller.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A view model for the User entity.
 */
@Getter
@Setter
@ToString
public class SignUpUserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 8, max = 8)
    private String rut;

    @NotNull
    @Size(min = 5, max = 255)
    private String email;

    @NotNull
    @Size(min = 6, max = 60)
    private String password;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    private Integer levelAccess = 0;
}

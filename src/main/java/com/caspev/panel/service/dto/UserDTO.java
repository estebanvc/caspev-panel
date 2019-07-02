package com.caspev.panel.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the User entity.
 */
@Getter
@Setter
@ToString
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(max = 36)
    private String uuid;

    @NotNull
    @Size(min = 8, max = 8)
    private String rut;

    @NotNull
    @Size(min = 5, max = 255)
    private String email;

    @NotNull
    @Size(min = 60, max = 60)
    private String password;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @NotNull
    private Integer levelAccess;
}

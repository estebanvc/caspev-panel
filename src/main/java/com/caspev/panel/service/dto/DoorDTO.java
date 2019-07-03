package com.caspev.panel.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the Door entity.
 */
@Getter
@Setter
@ToString
public class DoorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(max = 36)
    private String uuid;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    private Integer levelAccess;
}

package com.caspev.panel.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the EventLog entity.
 */
@Getter
@Setter
@ToString
public class EventLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(max = 36)
    private String uuid;

    @NotNull
    private Instant date;


    private VehicleDTO vehicle;

    private NfcCardDTO ncfCard;

    private DoorDTO door;

    private UserDTO user;

}

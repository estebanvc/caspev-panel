package com.caspev.panel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@ToString
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 36, max = 36)
    @Column(name = "uuid", length = 36, nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(name = "plate", length = 6, nullable = false, unique = true)
    private String plate;

    @ManyToOne
    @JsonIgnoreProperties("vehicles")
    private User user;

    public Vehicle() {

    }

    public Vehicle(@NotNull @Size(min = 6, max = 6) String plate) {
        this.plate = plate;
    }

    @PrePersist
    public void generateUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}
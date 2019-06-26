package com.caspev.panel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

/**
 * A Door.
 */
@Entity
@Table(name = "door")
@Getter
@Setter
@ToString
public class Door implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 36, max = 36)
    @Column(name = "uuid", length = 36, unique = true)
    private String uuid = UUID.randomUUID().toString();

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Column(name = "level_access", nullable = false)
    private Integer levelAccess;
}
package com.caspev.panel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * An EeventLog.
 */
@Entity
@Table(name = "event_log")
@Getter
@Setter
@ToString
public class EventLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 36, max = 36)
    @Column(name = "uuid", length = 36, nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private NfcCard ncfCard;

    @ManyToOne
    private Door door;

    @ManyToOne
    private User user;

    @PrePersist
    public void generateUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}
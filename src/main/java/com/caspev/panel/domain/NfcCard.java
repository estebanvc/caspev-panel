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

/**
 * A NfcCard.
 */
@Entity
@Table(name = "nfc_card")
@Getter
@Setter
@ToString
public class NfcCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 36, max = 36)
    @Column(name = "uuid", length = 36, nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "code", length = 11, nullable = false, unique = true)
    private String code;

    @ManyToOne
    @JsonIgnoreProperties("nfcCards")
    private User user;

    public NfcCard() {

    }

    public NfcCard(@NotNull @Size(min = 11, max = 11) String code) {
        this.code = code;
    }

    @PrePersist
    public void generateUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}
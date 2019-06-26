package com.caspev.panel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A user.
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User extends AuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 36, max = 36)
    @Column(name = "uuid", length = 36, unique = true)
    private String uuid = UUID.randomUUID().toString();

    @NotNull
    @Size(min = 8, max = 8)
    @Column(length = 8, unique = true, nullable = false)
    private String rut;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotNull
    @Size(min = 2, max = 6)
    @Column(name = "lang_key", length = 6, nullable = false)
    private String langKey;

    @NotNull
    @Column(name = "level_access", nullable = false)
    private Integer levelAccess;

    @JsonIgnore
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "user_role",
            joinColumns = {
                    @JoinColumn(
                            name = "user_id",
                            referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "role_id",
                            referencedColumnName = "id")
            })
    @BatchSize(size = 20)
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<NfcCard> nfcCards = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Vehicle> vehicles = new HashSet<>();

    /**
     * Add a role
     *
     * @param role the role
     * @return the persisted entity
     */
    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    /**
     * Remove a role
     *
     * @param role the role
     * @return the persisted entity
     */
    public User removeRole(Role role) {
        this.roles.remove(role);
        return this;
    }

}

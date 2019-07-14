package de.sappich.springsecuritydemo.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @NotNull @NotBlank
    @Column(unique = true)
    private String username;
    @NotNull @NotBlank
    private String password;
    @Column(updatable = false)
    ZonedDateTime created;
    ZonedDateTime modified;

    @PrePersist
    public void created() {
        this.created = ZonedDateTime.now(ZoneId.of("UTC"));
        this.modified = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    @PreUpdate
    public void updated() {
        this.modified = ZonedDateTime.now(ZoneId.of("UTC"));
    }
}

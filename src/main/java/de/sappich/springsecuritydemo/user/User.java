package de.sappich.springsecuritydemo.user;

import de.sappich.springsecuritydemo.admin.Authority;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "user_id")
    private String userId;
    @NotNull @NotBlank
    @Column(unique = true)
    private String username;
    @NotNull @NotBlank
    private String password;
    @Column(updatable = false)
    private ZonedDateTime created;
    private ZonedDateTime modified;
    private boolean isEnabled = true;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Authority> authorities = new HashSet<>();


    @PrePersist
    public void created() {
        this.created = ZonedDateTime.now(ZoneId.of("UTC"));
        this.modified = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    @PreUpdate
    public void updated() {
        this.modified = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public boolean addAuthority(String ... authorities) {
        final Set<Authority> auth = Stream.of(authorities)
                .map(authority -> new Authority(authority))
                .collect(Collectors.toSet());

        return this.authorities.addAll(auth);
    }
}

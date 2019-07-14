package de.sappich.springsecuritydemo.admin;

import de.sappich.springsecuritydemo.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "authority_id")
    private String id;

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }
}

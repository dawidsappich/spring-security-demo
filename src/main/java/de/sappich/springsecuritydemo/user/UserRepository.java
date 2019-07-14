package de.sappich.springsecuritydemo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<CustomUser, String> {
    Optional<CustomUser> findByUsername(String username);
}

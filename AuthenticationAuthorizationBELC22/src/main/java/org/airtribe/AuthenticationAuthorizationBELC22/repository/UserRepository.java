package org.airtribe.AuthenticationAuthorizationBELC22.repository;

import java.util.Optional;
import org.airtribe.AuthenticationAuthorizationBELC22.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}

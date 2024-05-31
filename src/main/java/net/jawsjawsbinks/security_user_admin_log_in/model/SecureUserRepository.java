package net.jawsjawsbinks.security_user_admin_log_in.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// extends JPA, use your user class and the identifier in this case Long for id
public interface SecureUserRepository extends JpaRepository<SecureUser, Long>{
    
    Optional<SecureUser> findByUsername(String username);
    
}

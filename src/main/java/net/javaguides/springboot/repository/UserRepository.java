package net.javaguides.springboot.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.javaguides.springboot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Méthodes corrigées
    Optional<User> findByResetToken(String token);
    User findByCodeParrainage(String codeParrainage);
    
    // Méthodes existantes valides
    boolean existsByEmail(String email);
    boolean existsByCin(String cin);
    List<User> findByStatus(String status);
    User findByEmail(String email);
}
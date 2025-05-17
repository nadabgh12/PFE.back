package net.javaguides.springboot.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.web.dto.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    void createPasswordResetTokenForUser(User user, String token);
    Optional<User> getUserByPasswordResetToken(String token);
    void resetUserPassword(User user, String newPassword);
    void sendPasswordResetEmail(User user);
    Optional<User> findById(Long id);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCin(String cin);
    List<User> findAll();
    List<User> findAllByStatus(String status);
    void approveUser(Long userId);
    void rejectUser(Long userId);
    User updateUser(User user);
    void deleteUser(Long id);
    User findByCodeParrainage(String codeParrainage);
	List<User> findByStatus(String status);
	void processForgotPassword(String email);

}
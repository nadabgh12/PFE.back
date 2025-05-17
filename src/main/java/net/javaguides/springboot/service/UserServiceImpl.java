package net.javaguides.springboot.service;

import net.javaguides.springboot.model.PasswordResetToken;
import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.PasswordResetTokenRepository;
import net.javaguides.springboot.repository.RoleRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.web.dto.UserRegistrationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           PasswordResetTokenRepository passwordResetTokenRepository,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        try {
            validateRegistrationData(registrationDto);
            User user = mapRegistrationDtoToUser(registrationDto);
            assignUserRoleAndSpecifics(user, registrationDto);
            User savedUser = userRepository.save(user);
            logger.info("User registered successfully with ID: {}", savedUser.getId());
            return savedUser;
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation while saving user", e);
            throw new IllegalStateException("Une donnée unique existe déjà : " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error during user registration", e);
            throw new RuntimeException("Erreur lors de l'enregistrement : " + e.getMessage(), e);
        }
    }

    private void validateRegistrationData(UserRegistrationDto dto) {
        if (dto.getEmail() == null || userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("Email déjà utilisé ou invalide : " + dto.getEmail());
        }
        if (dto.getCin() == null || userRepository.existsByCin(dto.getCin())) {
            throw new IllegalStateException("CIN déjà enregistré ou invalide : " + dto.getCin());
        }
        if (dto.getPassword() == null || dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 8 caractères");
        }
    }

    private User mapRegistrationDtoToUser(UserRegistrationDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setCin(dto.getCin());
        user.setBirthDate(dto.getBirthDate());
        user.setCountry(dto.getCountry());
        user.setUserType(dto.getUserType());
        return user;
    }

    private void assignUserRoleAndSpecifics(User user, UserRegistrationDto dto) {
        Set<Role> roles = new HashSet<>();
        String userType = dto.getUserType().toUpperCase();

        switch (userType) {
            case "AMBASSADEUR":
                Role ambassadeurRole = roleRepository.findByName("ROLE_AMBASSADEUR")
                        .orElseThrow(() -> new EntityNotFoundException("Rôle ambassadeur non configuré"));
                roles.add(ambassadeurRole);
                user.setTypeAmbassadeur(dto.getTypeAmbassadeur());
                user.setAssociation(dto.getAssociation());
                user.setStatus("EN_ATTENTE");
                break;

            case "PARRAINEUR":
                Role parraineurRole = roleRepository.findByName("ROLE_PARRAINEUR")
                        .orElseThrow(() -> new EntityNotFoundException("Rôle parraineur non configuré"));
                roles.add(parraineurRole);
                user.setGouvernorat(dto.getGouvernorat());
                user.setCodeParrainage(generateUniqueCode());
                user.setStatus("APPROUVE");
                break;

            default:
                throw new IllegalArgumentException("Type d'utilisateur invalide : " + userType);
        }

        user.setRoles(roles);
    }

    private String generateUniqueCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null || !"APPROUVE".equals(user.getStatus())) {
            throw new UsernameNotFoundException("Utilisateur non trouvé ou non approuvé");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    // Méthodes classiques
    @Override public Optional<User> findById(Long id) { return userRepository.findById(id); }
    @Override public User findByEmail(String email) { return userRepository.findByEmail(email); }
    @Override public boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }
    @Override public boolean existsByCin(String cin) { return userRepository.existsByCin(cin); }
    @Override public List<User> findAll() { return userRepository.findAll(); }
    @Override public List<User> findAllByStatus(String status) { return userRepository.findByStatus(status); }

    @Override
    public void approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setStatus("APPROUVE");
        userRepository.save(user);
    }

    @Override
    public void rejectUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setStatus("REJETE");
        userRepository.save(user);
    }

    @Override public User updateUser(User user) { return userRepository.save(user); }
    @Override public void deleteUser(Long id) { userRepository.deleteById(id); }
    @Override public User findByCodeParrainage(String codeParrainage) { return userRepository.findByCodeParrainage(codeParrainage); }
    @Override public List<User> findByStatus(String status) { return userRepository.findByStatus(status); }

    // Fonctionnalité : mot de passe oublié
    @Override
    public void processForgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l’email : " + email);
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(resetToken);

        String resetUrl = "http://localhost:8080/reset-password?token=" + token;

        String subject = "Réinitialisation du mot de passe";
        String body = "Bonjour " + user.getFirstName() + ",\n\n" +
                      "Cliquez ici pour réinitialiser votre mot de passe :\n" + resetUrl +
                      "\n\nCe lien expirera dans 1 heure.";

        emailService.sendSimpleEmail(user.getEmail(), subject, body);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(myToken);

        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        sendEmail(user.getEmail(), "Réinitialisation du mot de passe", "Cliquez ici pour réinitialiser votre mot de passe : " + resetLink);
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        if (resetToken != null && resetToken.getExpiryDate().after(new Date())) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public void resetUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private void sendEmail(String to, String subject, String text) {
        // Appel de ton EmailService
        emailService.sendSimpleEmail(to, subject, text);
        logger.info("Email envoyé à {} avec le sujet '{}'", to, subject);
    }

	@Override
	public void sendPasswordResetEmail(User user) {
		// TODO Auto-generated method stub
		
	}
}

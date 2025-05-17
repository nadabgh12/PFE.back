/*package net.javaguides.springboot.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserService;
import net.javaguides.springboot.web.dto.UserRegistrationDto;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/registration")
public class RegistrationApiController {

    private final UserService userService;

    public RegistrationApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/ambassadeur")
    public ResponseEntity<Map<String, Object>> registerAmbassadeur(
            @RequestBody UserRegistrationDto registrationDto) {
        return handleRegistration(registrationDto, "AMBASSADEUR");
    }

    @PostMapping("/parraineur")
    public ResponseEntity<Map<String, Object>> registerParraineur(
            @RequestBody UserRegistrationDto registrationDto) {
        return handleRegistration(registrationDto, "PARRAINEUR");
    }

    private ResponseEntity<Map<String, Object>> handleRegistration(
            UserRegistrationDto registrationDto, String userType) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            registrationDto.setUserType(userType);
            userService.save(registrationDto);
            
            response.put("status", "success");
            response.put("message", "Inscription réussie");
            response.put("userType", userType);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            response.put("userType", userType);
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/gouvernorats")
    public ResponseEntity<Map<String, Object>> getGouvernorats() {
        Map<String, Object> response = new HashMap<>();
        response.put("gouvernorats", Arrays.asList(
            "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès",
            "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kébili",
            "Kef", "Mahdia", "Manouba", "Médenine", "Monastir",
            "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse",
            "Tataouine", "Tozeur", "Tunis", "Zaghouan"
        ));
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        User user = userService.findByEmail(email);  // Assurez-vous d'avoir cette méthode dans votre UserService
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email non trouvé");
        }

        // Générer un token aléatoire
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        
        // Envoi de l'email ici (assurez-vous que l'email est bien envoyé via un service d'email)
        // emailService.sendPasswordResetEmail(user.getEmail(), token);
        
        return ResponseEntity.ok("Un email avec un lien de réinitialisation a été envoyé.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Optional<User> userOpt = userService.getUserByPasswordResetToken(token);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token invalide ou expiré");
        }

        User user = userOpt.get();
        userService.resetUserPassword(user, newPassword);

        return ResponseEntity.ok("Mot de passe réinitialisé avec succès");
    }
}*/
package net.javaguides.springboot.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.EmailService;
import net.javaguides.springboot.service.UserService;
import net.javaguides.springboot.web.dto.UserRegistrationDto;
import org.springframework.http.HttpStatus;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/registration")
public class RegistrationApiController {

    private final UserService userService;
    private final EmailService emailService;

    public RegistrationApiController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/ambassadeur")
    public ResponseEntity<Map<String, Object>> registerAmbassadeur(
            @RequestBody UserRegistrationDto registrationDto) {
        return handleRegistration(registrationDto, "AMBASSADEUR");
    }

    @PostMapping("/parraineur")
    public ResponseEntity<Map<String, Object>> registerParraineur(
            @RequestBody UserRegistrationDto registrationDto) {
        return handleRegistration(registrationDto, "PARRAINEUR");
    }

    private ResponseEntity<Map<String, Object>> handleRegistration(
            UserRegistrationDto registrationDto, String userType) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            registrationDto.setUserType(userType);
            userService.save(registrationDto);
            response.put("status", "success");
            response.put("message", "Inscription réussie");
            response.put("userType", userType);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            response.put("userType", userType);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/gouvernorats")
    public ResponseEntity<Map<String, Object>> getGouvernorats() {
        Map<String, Object> response = new HashMap<>();
        response.put("gouvernorats", Arrays.asList(
            "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès",
            "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kébili",
            "Kef", "Mahdia", "Manouba", "Médenine", "Monastir",
            "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse",
            "Tataouine", "Tozeur", "Tunis", "Zaghouan"
        ));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email non trouvé");
        }

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        // Envoi d’un email avec le lien contenant le token
        String resetUrl = "http://localhost:4200/reset-password?token=" + token;
        String subject = "Réinitialisation de votre mot de passe";
        String body = "Cliquez sur ce lien pour réinitialiser votre mot de passe : " + resetUrl;

        emailService.sendEmail(user.getEmail(), subject, body);

        return ResponseEntity.ok("Un email avec un lien de réinitialisation a été envoyé.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Optional<User> userOpt = userService.getUserByPasswordResetToken(token);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token invalide ou expiré");
        }

        User user = userOpt.get();
        userService.resetUserPassword(user, newPassword);

        return ResponseEntity.ok("Mot de passe réinitialisé avec succès");
    }

}


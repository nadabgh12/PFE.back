package net.javaguides.springboot.web;

import net.javaguides.springboot.model.PasswordResetToken;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.PasswordResetTokenRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import net.javaguides.springboot.web.dto.UserRegistrationDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class MainController {

    private final UserService userService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MainController(UserService userService,
                          PasswordResetTokenRepository passwordResetTokenRepository,
                          UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Page d'accueil
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Page de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Formulaire d'inscription pour ambassadeur
    @GetMapping("/registration/ambassadeur")
    public String showAmbassadeurForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration-ambassadeur";
    }

    // Traitement inscription ambassadeur
    @PostMapping("/registration/ambassadeur")
    public String registerAmbassadeur(@ModelAttribute("user") UserRegistrationDto registrationDto,
                                      BindingResult bindingResult,
                                      Model model) {
        if (bindingResult.hasErrors()) {
            return "registration-ambassadeur";
        }

        try {
            registrationDto.setUserType("AMBASSADEUR");
            userService.save(registrationDto);
            return "redirect:/registration/success?type=ambassadeur";
        } catch (Exception e) {
            model.addAttribute("error", "Une erreur est survenue lors de l'enregistrement.");
            return "registration-ambassadeur";
        }
    }

    // Formulaire d'inscription pour parraineur
    @GetMapping("/registration/parraineur")
    public String showParraineurForm(Model model) {
        model.addAttribute("gouvernorats", getGouvernorats());
        model.addAttribute("user", new UserRegistrationDto());
        return "registration-parraineur";
    }

    // Traitement inscription parraineur
    @PostMapping("/registration/parraineur")
    public String registerParraineur(@ModelAttribute("user") UserRegistrationDto registrationDto,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("gouvernorats", getGouvernorats());
            return "registration-parraineur";
        }

        try {
            registrationDto.setUserType("PARRAINEUR");
            userService.save(registrationDto);
            return "redirect:/registration/success?type=parraineur";
        } catch (Exception e) {
            model.addAttribute("error", "Une erreur est survenue lors de l'enregistrement.");
            model.addAttribute("gouvernorats", getGouvernorats());
            return "registration-parraineur";
        }
    }

    // Page de succès
    @GetMapping("/registration/success")
    public String registrationSuccess(@RequestParam String type, Model model) {
        model.addAttribute("userType", type);
        return "registration-success";
    }

    // Afficher formulaire de réinitialisation
    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token).orElse(null);

        if (resetToken == null || resetToken.isExpired()) {
            model.addAttribute("error", "Token invalide ou expiré.");
            return "reset-password";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    // Traitement de la réinitialisation
    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam("token") String token,
                                      @RequestParam("password") String newPassword,
                                      Model model) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token).orElse(null);

        if (resetToken == null || resetToken.isExpired()) {
            model.addAttribute("error", "Token invalide ou expiré.");
            return "reset-password";
        }

        User user = (User) resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);

        model.addAttribute("message", "Mot de passe mis à jour avec succès !");
        return "login";
    }

    // Liste des gouvernorats
    private List<String> getGouvernorats() {
        return Arrays.asList(
                "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès",
                "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kébili",
                "Kef", "Mahdia", "Manouba", "Médenine", "Monastir",
                "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse",
                "Tataouine", "Tozeur", "Tunis", "Zaghouan"
        );
    }
}

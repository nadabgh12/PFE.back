package net.javaguides.springboot.service;

import net.javaguides.springboot.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String toEmail, String resetUrl) {
        String subject = "Réinitialisation de votre mot de passe - AHMINI";
        String content = "<p>Bonjour,</p>"
                + "<p>Vous avez demandé à réinitialiser votre mot de passe.</p>"
                + "<p>Cliquez sur le lien ci-dessous pour le réinitialiser :</p>"
                + "<p><a href=\"" + resetUrl + "\">Réinitialiser mon mot de passe</a></p>"
                + "<br>"
                + "<p>Si vous n'avez pas fait cette demande, ignorez simplement ce message.</p>"
                + "<p>Merci,</p><p>L'équipe AHMINI</p>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            
            helper.setFrom("monemail@gmail.com");  // Ton adresse expéditrice
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Échec d'envoi de l'email : " + e.getMessage());
        }
    }
}

package net.javaguides.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    public static final JavaMailSender mailSender = null;
    
    public default void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("ton.email@gmail.com"); // doit correspondre à spring.mail.username

        mailSender.send(message); 
    }

	public default void sendEmail(String email, String subject, String body) {
		// TODO Auto-generated method stub
		
	}

}



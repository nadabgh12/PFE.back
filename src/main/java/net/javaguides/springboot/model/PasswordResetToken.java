

package net.javaguides.springboot.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToOne
    private User user;

    private Date expiryDate;

    public PasswordResetToken() {}

    public PasswordResetToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expiryDate = new Date(System.currentTimeMillis() + 3600000); // 1 hour expiration time
    }

	public Date getExpiryDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public PasswordResetToken orElse(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isExpired() {
		// TODO Auto-generated method stub
		return false;
	}

    // Getters and setters
}

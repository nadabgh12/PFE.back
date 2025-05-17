package net.javaguides.springboot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "cin")
})
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    private String email;
    private String password;
    private String phone;
    private String cin;
    
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    
    private String country;
    
    @Column(name = "user_type") // Ajouté
    private String userType;
    
    private String typeAmbassadeur;
    private String association;
    private String gouvernorat;
    private String codeParrainage;
    private String status = "EN_ATTENTE";
    
    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiration")
    private LocalDateTime resetTokenExpiration;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) // Cascade modifié
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // Constructeurs
    public User() {
    }

    public User(String firstName, String lastName, String email, String password, 
               String phone, String cin, Date birthDate, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.cin = cin;
        this.birthDate = birthDate;
        this.country = country;
    }

    // Getters/Setters valides pour les nouveaux champs
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getResetTokenExpiration() {
        return resetTokenExpiration;
    }

    public void setResetTokenExpiration(LocalDateTime resetTokenExpiration) {
        this.resetTokenExpiration = resetTokenExpiration;
    }

    // Getters/Setters pour userType
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    // Suppression des méthodes inutiles
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    // toString() amélioré
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", userType='" + userType + '\'' +
                ", resetTokenExpiration=" + resetTokenExpiration +
                '}';
    }

    public void setStatus(String string) {
        this.status = string;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTypeAmbassadeur() {
        return typeAmbassadeur;
    }

    public void setTypeAmbassadeur(String typeAmbassadeur) {
        this.typeAmbassadeur = typeAmbassadeur;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public String getCodeParrainage() {
        return codeParrainage;
    }

    public void setCodeParrainage(String codeParrainage) {
        this.codeParrainage = codeParrainage;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

	public Object getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLastName(String lastName2) {
		// TODO Auto-generated method stub
		
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public User orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}

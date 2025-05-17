/*package net.javaguides.springboot.web.dto;

import java.util.Date;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

public class UserRegistrationDto {

    // Champs communs
    @NotBlank(message = "Le prénom est obligatoire")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    private String lastName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit avoir au moins 8 caractères")
    private String password;

    @NotBlank(message = "Confirmez le mot de passe")
    private String confirmPassword;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String phone;

    @NotBlank(message = "Le CIN est obligatoire")
    @Pattern(regexp = "\\d{8}", message = "Le CIN doit contenir exactement 8 chiffres")
    private String cin;

    @NotNull(message = "La date de naissance est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La date de naissance doit être dans le passé")
    private Date birthDate;

    @NotBlank(message = "Le pays est obligatoire")
    private String country;

    // Champs spécifiques aux ambassadeurs
    private String typeAmbassadeur;
    private String association;

    // Champs spécifiques aux parraineurs
    private String gouvernorat;

    @NotBlank(message = "Le type d'utilisateur est obligatoire")
    private String userType;

    // Getters & Setters complets
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPrenom() {
		// TODO Auto-generated method stub
		return null;
	}
}*/



package net.javaguides.springboot.web.dto;

import java.util.Date;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

// Exemple : validateur pour comparer password et confirmPassword
@PasswordMatches  // Tu devras créer cette annotation personnalisée
public class UserRegistrationDto {

    // Champs communs
    @NotBlank(message = "Le prénom est obligatoire")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    private String lastName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit avoir au moins 8 caractères")
    private String password;

    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    private String confirmPassword;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String phone;

    @NotBlank(message = "Le CIN est obligatoire")
    @Pattern(regexp = "\\d{8}", message = "Le CIN doit contenir exactement 8 chiffres")
    private String cin;

    @NotNull(message = "La date de naissance est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La date de naissance doit être dans le passé")
    private Date birthDate;

    @NotBlank(message = "Le pays est obligatoire")
    private String country;

    @NotBlank(message = "Le type d'utilisateur est obligatoire")
    private String userType;

    // Champs spécifiques
    private String typeAmbassadeur;

    private String association;

    private String gouvernorat;

    // Getters & Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getTypeAmbassadeur() { return typeAmbassadeur; }
    public void setTypeAmbassadeur(String typeAmbassadeur) { this.typeAmbassadeur = typeAmbassadeur; }

    public String getAssociation() { return association; }
    public void setAssociation(String association) { this.association = association; }

    public String getGouvernorat() { return gouvernorat; }
    public void setGouvernorat(String gouvernorat) { this.gouvernorat = gouvernorat; }
}

package net.javaguides.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Femme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String dateNaissance;
    private String gouvernorat;
    private String cin;
    private int nbrEnfants;
    private String activite;
    private String cinFilePath;
	public void setNom(String nom2) {
		// TODO Auto-generated method stub
		
	}
	public void setPrenom(Object prenom2) {
		// TODO Auto-generated method stub
		
	}
	public void setDateNaissance(Object dateNaissance2) {
		// TODO Auto-generated method stub
		
	}
	public void setGouvernorat(String gouvernorat2) {
		// TODO Auto-generated method stub
		
	}
	public void setCin(String cin2) {
		// TODO Auto-generated method stub
		
	}
	public void setNbrEnfants(Object nbrEnfants2) {
		// TODO Auto-generated method stub
		
	}
	public void setActivite(Object activite2) {
		// TODO Auto-generated method stub
		
	}
	public void setCinFilePath(String string) {
		// TODO Auto-generated method stub
		
	}

    // Getters & Setters
}

package net.javaguides.springboot.model;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Parrainage {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String nomFilleule;
 private int age;
 private String gouvernorat;
 private double montant;
 private LocalDate dateParrainage;
public String getNomFilleule() {
	return nomFilleule;
}
public void setNomFilleule(String nomFilleule) {
	this.nomFilleule = nomFilleule;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public String getGouvernorat() {
	return gouvernorat;
}
public void setGouvernorat(String gouvernorat) {
	this.gouvernorat = gouvernorat;
}
public double getMontant() {
	return montant;
}
public void setMontant(double montant) {
	this.montant = montant;
}
public LocalDate getDateParrainage() {
	return dateParrainage;
}
public void setDateParrainage(LocalDate dateParrainage) {
	this.dateParrainage = dateParrainage;
}

 // Constructeur, Getters, Setters
}

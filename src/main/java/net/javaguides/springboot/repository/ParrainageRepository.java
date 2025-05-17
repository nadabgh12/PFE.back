package net.javaguides.springboot.repository;



import java.time.LocalDate;
import java.util.List;

//ParrainageRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.Parrainage;

public interface ParrainageRepository extends JpaRepository<Parrainage, Long> {
 // Tu peux ajouter des méthodes comme : findByNomFilleule, etc.
	List<Parrainage> findByGouvernoratContainingIgnoreCase(String gouvernorat);
	List<Parrainage> findByMontantGreaterThanEqual(double montant);
	List<Parrainage> findByNomFilleuleContainingIgnoreCase(String nom);
	List<Parrainage> findByDateParrainage(LocalDate date);

}

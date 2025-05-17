package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.Femme;

public interface FemmeRepository extends JpaRepository<Femme, Long> {
    List<Femme> findByCin(String cin);
    List<Femme> findByGouvernorat(String gouvernorat);
    boolean existsByCin(String cin);

}


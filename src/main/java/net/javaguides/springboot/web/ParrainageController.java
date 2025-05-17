package net.javaguides.springboot.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.model.Parrainage;
import net.javaguides.springboot.repository.ParrainageRepository;
import net.javaguides.springboot.web.dto.FemmeDto;

@RestController
@RequestMapping("/api/parrainage")
public class ParrainageController {

    private final ParrainageRepository repo;

    public ParrainageController(ParrainageRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/payer")
    public ResponseEntity<String> payer(@RequestBody FemmeDto femme) {
        Parrainage p = new Parrainage();
        p.setNomFilleule(femme.getNom());
        p.setAge(femme.getAge());
        p.setGouvernorat(femme.getGouvernorat());
        p.setMontant(femme.getMontant());
        p.setDateParrainage(LocalDate.now());
        repo.save(p); // ⚠️ enregistrement dans PostgreSQL


return ResponseEntity.ok("Parrainage enregistré en base !");
    }
    @GetMapping("/rechercher")
    public List<Parrainage> rechercher(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String gouvernorat,
            @RequestParam(required = false) Double montantMin) {

        if (nom != null) return repo.findByNomFilleuleContainingIgnoreCase(nom);
        if (date != null) return repo.findByDateParrainage(date);
        if (gouvernorat != null) return repo.findByGouvernoratContainingIgnoreCase(gouvernorat);
        if (montantMin != null) return repo.findByMontantGreaterThanEqual(montantMin);

        return repo.findAll();
    }

}

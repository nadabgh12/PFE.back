package net.javaguides.springboot.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.javaguides.springboot.model.Femme;
import net.javaguides.springboot.repository.FemmeRepository;
import net.javaguides.springboot.web.dto.FemmeDto;

@RestController
@RequestMapping("/api/femmes")
@CrossOrigin(origins = "*")
public class FemmeController {

    private final FemmeRepository repo;

    public FemmeController(FemmeRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/inscription")
    public ResponseEntity<String> inscrireFemme(
            @RequestPart("donnees") FemmeDto femmeDto,
            @RequestPart("fileCIN") MultipartFile fileCIN) {

        try {
            if (repo.existsByCin(femmeDto.getCin())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("❌ Cette CIN est déjà enregistrée !");
            }

            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));
            String fileName = UUID.randomUUID() + "_" + fileCIN.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, fileCIN.getBytes());

            Femme femme = new Femme();
            femme.setNom(femmeDto.getNom());
            femme.setPrenom(femmeDto.getPrenom());
            femme.setDateNaissance(femmeDto.getDateNaissance());
            femme.setGouvernorat(femmeDto.getGouvernorat());
            femme.setCin(femmeDto.getCin());
            femme.setNbrEnfants(femmeDto.getNbrEnfants());
            femme.setActivite(femmeDto.getActivite());
            femme.setCinFilePath(filePath.toString());

            repo.save(femme);
            return ResponseEntity.ok("✅ Femme enregistrée !");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Erreur serveur");
        }
    }
}

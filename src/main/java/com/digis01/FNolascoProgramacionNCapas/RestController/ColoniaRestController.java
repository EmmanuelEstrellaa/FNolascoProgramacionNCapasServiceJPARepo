package com.digis01.FNolascoProgramacionNCapas.RestController;

import com.digis01.FNolascoProgramacionNCapas.DAO.ColoniaDAOImplementation;
import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coloniaapi")
public class ColoniaRestController {

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

    @GetMapping("/bymunicipio/{IdMunicipio}")
    public ResponseEntity GetByIdMunicipio(@PathVariable int IdMunicipio) {
        Result result = coloniaDAOImplementation.ColoniaByIdMunicipioJPA(IdMunicipio);

        if (result.correct) {
            if (result.correct = false) {
                return ResponseEntity.status(204).body(null);
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

}

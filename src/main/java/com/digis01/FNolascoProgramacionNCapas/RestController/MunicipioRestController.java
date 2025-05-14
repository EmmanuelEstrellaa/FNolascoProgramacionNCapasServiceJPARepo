package com.digis01.FNolascoProgramacionNCapas.RestController;

import com.digis01.FNolascoProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/municipioapi")
public class MunicipioRestController {

    @Autowired
    private MunicipioDAOImplementation municipioDAOImpementation;

    @GetMapping("/byestado/{IdEstado}")
    public ResponseEntity GetByIdEstado(@PathVariable int IdEstado) {
        Result result = municipioDAOImpementation.MunicipioByIdEstadoJPA(IdEstado);

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

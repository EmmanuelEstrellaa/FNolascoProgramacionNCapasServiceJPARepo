package com.digis01.FNolascoProgramacionNCapas.RestController;

import com.digis01.FNolascoProgramacionNCapas.DAO.EstadoDAOImplementation;
import com.digis01.FNolascoProgramacionNCapas.DataAccesObject.EstadoDAO;
import com.digis01.FNolascoProgramacionNCapas.JPA.Estado;
import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estadoapi")
public class EstadoRestController {

    @Autowired
    private EstadoDAO EstadoDAO;

    @GetMapping("/bypais/{idPais}")
    public ResponseEntity<List<Estado>> EstadoByid(@PathVariable int idPais) {
        List<Estado> estados = EstadoDAO.findByPaisIdPais(idPais);
        if (estados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estados);
    }

}

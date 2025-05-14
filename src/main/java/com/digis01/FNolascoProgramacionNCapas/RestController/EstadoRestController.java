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

    @GetMapping("/bypais/{IdPais}")
    public ResponseEntity<Estado> EstadoByid(@PathVariable int IdPais) {
        return EstadoDAO.findById(IdPais)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

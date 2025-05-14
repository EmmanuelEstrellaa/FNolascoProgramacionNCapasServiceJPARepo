package com.digis01.FNolascoProgramacionNCapas.RestController;

import com.digis01.FNolascoProgramacionNCapas.DAO.PaisDAOImplementation;
import com.digis01.FNolascoProgramacionNCapas.DataAccesObject.PaisDAO;
import com.digis01.FNolascoProgramacionNCapas.JPA.Pais;
import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paisapi")
public class PaisRestController {

    @Autowired
    private PaisDAO PaisDAO;


    @GetMapping
    public List<Pais> GetAllRepo() {
        return PaisDAO.findAll();
    }

}

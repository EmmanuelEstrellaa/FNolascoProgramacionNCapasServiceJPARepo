package com.digis01.FNolascoProgramacionNCapas.RestController;

import com.digis01.FNolascoProgramacionNCapas.DAO.RollDAOImplementation;
import com.digis01.FNolascoProgramacionNCapas.DataAccesObject.RollDAO;
import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import com.digis01.FNolascoProgramacionNCapas.JPA.Roll;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rollapi")
public class RollRestController {

    @Autowired
    private RollDAOImplementation rollDAOImplementation;
    
    @Autowired
    private RollDAO RollDAO;

//    @GetMapping
//    public ResponseEntity GetAll() {
//        Result result = rollDAOImplementation.GetAllJPA();
//
//        if (result.correct) {
//            if (result.correct = false) {
//                return ResponseEntity.status(204).body(null);
//            } else {
//                return ResponseEntity.ok(result);
//            }
//        } else {
//            return ResponseEntity.status(404).body(null);
//        }
//
//    }

    @GetMapping("/getall")
    public List<Roll> GetAllRepo() {
        return RollDAO.findAll();
    }

}

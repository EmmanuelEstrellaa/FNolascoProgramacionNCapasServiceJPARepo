package com.digis01.FNolascoProgramacionNCapas.DataAccesObject;

import com.digis01.FNolascoProgramacionNCapas.JPA.Pais;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaisDAO extends JpaRepository<Pais, Integer>{

    
}


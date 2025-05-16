package com.digis01.FNolascoProgramacionNCapas.DataAccesObject;

import com.digis01.FNolascoProgramacionNCapas.JPA.Estado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoDAO extends JpaRepository<Estado, Integer>{
    
    List<Estado> findByPaisIdPais(int idPais);
    
}

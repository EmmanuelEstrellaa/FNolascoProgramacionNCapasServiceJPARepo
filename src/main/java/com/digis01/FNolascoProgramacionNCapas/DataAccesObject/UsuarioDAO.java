package com.digis01.FNolascoProgramacionNCapas.DataAccesObject;

import com.digis01.FNolascoProgramacionNCapas.JPA.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer>{
    
    
}

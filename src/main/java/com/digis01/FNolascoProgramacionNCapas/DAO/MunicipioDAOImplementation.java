
package com.digis01.FNolascoProgramacionNCapas.DAO;

import com.digis01.FNolascoProgramacionNCapas.JPA.Municipio;
import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipioDAO{
    
    @Autowired
    private EntityManager entityManager;
    
    
    @Override
    public Result MunicipioByIdEstadoJPA(int IdEstado) {
        Result result = new Result();
        
        try{
            
            TypedQuery<Municipio> queryMunicipio = entityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :idEstado", Municipio.class);
            queryMunicipio.setParameter("idEstado", IdEstado);
            result.object = queryMunicipio.getResultList();
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }


        return result;
    }
    
    
}


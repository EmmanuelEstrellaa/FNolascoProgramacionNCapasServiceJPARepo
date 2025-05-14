
package com.digis01.FNolascoProgramacionNCapas.DAO;

import com.digis01.FNolascoProgramacionNCapas.JPA.Colonia;
import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ColoniaDAOImplementation implements IColoniaDAO{
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
     public Result ColoniaByIdMunicipioJPA(int IdMunicipio) {
        Result result = new Result();
        
        try{
            
            TypedQuery<Colonia> queryColonia = entityManager.createQuery("FROM Colonia WHERE Municipio.IdMunicipio = :idMunicipio", Colonia.class);
            queryColonia.setParameter("idMunicipio", IdMunicipio);
            result.object = queryColonia.getResultList();
            
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
     
     @Override
     public Result ColoniaByCPJPA(int CodigoPostal) {
        Result result = new Result();



        return result;
    }
}

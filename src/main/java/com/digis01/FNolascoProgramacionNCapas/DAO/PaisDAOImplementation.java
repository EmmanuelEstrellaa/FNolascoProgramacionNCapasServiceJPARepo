
package com.digis01.FNolascoProgramacionNCapas.DAO;

import com.digis01.FNolascoProgramacionNCapas.JPA.Pais;
import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaisDAOImplementation implements IPaisDAO {

    
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAllJPA() {
        Result result = new Result();
        
        try{
            
            TypedQuery<Pais> queryPais = entityManager.createQuery("FROM Pais", Pais.class);
            result.object = queryPais.getResultList();
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
    
    
    

}

package com.digis01.FNolascoProgramacionNCapas.DAO;

import com.digis01.FNolascoProgramacionNCapas.JPA.Estado;
import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstadoDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result EstadoByIdPaisJPA(int IdPais) {
        Result result = new Result();
        
        try{
            
            TypedQuery<Estado> queryEstado = entityManager.createQuery("FROM Estado WHERE Pais.IdPais = :idPais", Estado.class);
            queryEstado.setParameter("idPais", IdPais);
            result.object = queryEstado.getResultList();
            result.correct = true;
            
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        
        return result;
    }

}

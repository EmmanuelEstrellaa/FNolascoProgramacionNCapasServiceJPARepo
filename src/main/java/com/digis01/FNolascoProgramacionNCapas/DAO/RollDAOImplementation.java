package com.digis01.FNolascoProgramacionNCapas.DAO;

import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import com.digis01.FNolascoProgramacionNCapas.JPA.Roll;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;

@Repository
public class RollDAOImplementation implements IRollDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAllJPA() {
        Result result = new Result();

        try{
            
            TypedQuery<Roll> queryRoll = entityManager.createQuery("FROM Roll", Roll.class);
            result.object = queryRoll.getResultList();
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}

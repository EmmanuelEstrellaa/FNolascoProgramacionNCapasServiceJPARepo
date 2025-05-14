package com.digis01.FNolascoProgramacionNCapas.DAO;

import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import java.sql.ResultSet;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result DireccionByIdJPA(int IdDireccion) {
        Result result = new Result();

       

        return result;
    }


}

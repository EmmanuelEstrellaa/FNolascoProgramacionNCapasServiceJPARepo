
package com.digis01.FNolascoProgramacionNCapas.DAO;

import com.digis01.FNolascoProgramacionNCapas.JPA.Result;



public interface IColoniaDAO {
    Result ColoniaByIdMunicipioJPA(int IdMunicipio);
    Result ColoniaByCPJPA(int CodigoPostal);
}

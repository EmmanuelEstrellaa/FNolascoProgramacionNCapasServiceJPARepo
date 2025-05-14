
package com.digis01.FNolascoProgramacionNCapas.DAO;

import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import com.digis01.FNolascoProgramacionNCapas.JPA.Usuario;
import com.digis01.FNolascoProgramacionNCapas.JPA.UsuarioDireccion;

public interface IUsuarioDAO {
    Result UsuarioGetByIdJPA(int IdUsuario);
    Result GetAllJPA();
    Result AddJPA(UsuarioDireccion usuarioDireccion);
    Result AddDireccionJPA(UsuarioDireccion usuarioDireccion);
    Result UsuaDirByIdJPA(int IdUsuario);
    Result UsuarioUpdateJPA(Usuario usuario);
//    Result DieccionUpdateJPA(UsuarioDireccion usuarioDireccion);
    Result DireccionDeleteJPA(int IdDireccion);
    Result DeleteUsuarioDireccionJPA(int IdUsuario);
    Result GetAllDinamico(Usuario usuario);
}

package com.digis01.FNolascoProgramacionNCapas.DAO;

import com.digis01.FNolascoProgramacionNCapas.JPA.Colonia;
import com.digis01.FNolascoProgramacionNCapas.JPA.Direccion;
import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import com.digis01.FNolascoProgramacionNCapas.JPA.Usuario;
import com.digis01.FNolascoProgramacionNCapas.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result UsuarioGetByIdJPA(int IdUsuario) {
        Result result = new Result();

        try {

            TypedQuery<Direccion> queryDireccionesUsuario = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
            queryDireccionesUsuario.setParameter("idusuario", IdUsuario);
            List<Direccion> direcciones = queryDireccionesUsuario.getResultList();

            entityManager.find(Usuario.class, IdUsuario);

            result.object = direcciones;

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Override
    public Result GetAllJPA() {

        Result result = new Result();
        try {
            TypedQuery<com.digis01.FNolascoProgramacionNCapas.JPA.Usuario> queryUsuarios = entityManager.createQuery("FROM Usuario", com.digis01.FNolascoProgramacionNCapas.JPA.Usuario.class);
            List<com.digis01.FNolascoProgramacionNCapas.JPA.Usuario> usuarios = queryUsuarios.getResultList();

            result.objects = new ArrayList<>();
            for (com.digis01.FNolascoProgramacionNCapas.JPA.Usuario usuario : usuarios) {

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = usuario;

                TypedQuery<com.digis01.FNolascoProgramacionNCapas.JPA.Direccion> queryDireccion = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", com.digis01.FNolascoProgramacionNCapas.JPA.Direccion.class);
                queryDireccion.setParameter("idusuario", usuario.getIdUsuario());

                List<com.digis01.FNolascoProgramacionNCapas.JPA.Direccion> direccionesJPA = queryDireccion.getResultList();
                usuarioDireccion.Direcciones = direccionesJPA;

                result.objects.add(usuarioDireccion);

            }

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;

    }

    @Transactional
    @Override
    public Result AddJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            entityManager.persist(usuarioDireccion.Usuario);

            usuarioDireccion.Direccion.Usuario = usuarioDireccion.Usuario;
            entityManager.persist(usuarioDireccion.Direccion);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result UsuaDirByIdJPA(int IdUsuario) {
        Result result = new Result();

        try {
            TypedQuery<Usuario> queryUsuarios = entityManager.createQuery("FROM Usuario WHERE IdUsuario = :idusuario", Usuario.class);
            queryUsuarios.setParameter("idusuario", IdUsuario);
            Usuario usuario = queryUsuarios.getSingleResult();

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = usuario;

            TypedQuery<Direccion> queryDireccion = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
            queryDireccion.setParameter("idusuario", usuario.getIdUsuario());

            List<Direccion> direcciones = queryDireccion.getResultList();
            usuarioDireccion.Direcciones = direcciones;
            result.object = usuarioDireccion;

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result AddDireccionJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {

            com.digis01.FNolascoProgramacionNCapas.JPA.Direccion direccionJPA
                    = new com.digis01.FNolascoProgramacionNCapas.JPA.Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());

            direccionJPA.Colonia = new com.digis01.FNolascoProgramacionNCapas.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());

            direccionJPA.Usuario = new com.digis01.FNolascoProgramacionNCapas.JPA.Usuario();
            direccionJPA.Usuario.setIdUsuario(usuarioDireccion.Usuario.getIdUsuario());
            entityManager.persist(direccionJPA);

            System.out.println("");

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result UsuarioUpdateJPA(Usuario usuario) {
        Result result = new Result();

        try {
            entityManager.merge(usuario);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

//    @Transactional
//    @Override
//    public Result DieccionUpdateJPA(UsuarioDireccion usuarioDireccion) {
//        Result result = new Result();
//
//        try {
//
//            com.digis01.FNolascoProgramacionNCapas.JPA.Direccion direccionJPA = new com.digis01.FNolascoProgramacionNCapas.JPA.Direccion();
////            direccionJPA = entityManager.find(com.digis01.FNolascoProgramacionNCapas.JPA.Direccion.class, direccion.getIdDireccion());
//
//            direccionJPA.setIdDireccion(usuarioDireccion.Direccion.getIdDireccion());
//            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
//            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
//            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
//
//            direccionJPA.Colonia = new com.digis01.FNolascoProgramacionNCapas.JPA.Colonia();
//            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());
//            direccionJPA.Colonia.setNombre(usuarioDireccion.Direccion.Colonia.getNombre());
//            direccionJPA.Colonia.setCodigoPostal(usuarioDireccion.Direccion.Colonia.getCodigoPostal());
//
//            direccionJPA.Colonia.Municipio = new com.digis01.FNolascoProgramacionNCapas.JPA.Municipio();
//            direccionJPA.Colonia.Municipio.setIdMunicipio(usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio());
//
//            direccionJPA.Colonia.Municipio.Estado = new com.digis01.FNolascoProgramacionNCapas.JPA.Estado();
//            direccionJPA.Colonia.Municipio.Estado.setIdEstado(usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado());
//
//            direccionJPA.Colonia.Municipio.Estado.Pais = new com.digis01.FNolascoProgramacionNCapas.JPA.Pais();
//            direccionJPA.Colonia.Municipio.Estado.Pais.setIdPais(usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais());
//
//            direccionJPA.Usuario = new com.digis01.FNolascoProgramacionNCapas.JPA.Usuario();
//            direccionJPA.Usuario.setIdUsuario(usuarioDireccion.Usuario.getIdUsuario());
//
//            //vaciar alumno ML a alumno JPA
//            entityManager.merge(direccionJPA);
//
//            System.out.println("");
//
//            result.correct = true;
//
//        } catch (Exception ex) {
//            result.correct = false;
//            result.errorMessage = ex.getLocalizedMessage();
//            result.ex = ex;
//        }
//
//        return result;
//    }
    @Transactional
    @Override
    public Result DireccionDeleteJPA(int IdDireccion) {
        Result result = new Result();

        try {

            com.digis01.FNolascoProgramacionNCapas.JPA.Direccion direccion = new com.digis01.FNolascoProgramacionNCapas.JPA.Direccion();
            direccion = entityManager.find(com.digis01.FNolascoProgramacionNCapas.JPA.Direccion.class, IdDireccion);

            entityManager.remove(direccion);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result DeleteUsuarioDireccionJPA(int IdUsuario) {
        Result result = new Result();

        try {

            TypedQuery<Direccion> queryDireccionesUsuario = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
            queryDireccionesUsuario.setParameter("idusuario", IdUsuario);
            List<Direccion> direcciones = queryDireccionesUsuario.getResultList();

            Usuario usuario = entityManager.find(Usuario.class, IdUsuario);

            for (Direccion direccione : direcciones) {
                entityManager.remove(direccione);
            }

            entityManager.remove(usuario);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;

    }

    @Override
    public Result GetAllDinamico(Usuario usuario) {
        Result result = new Result();

        try {
            String queryDinamico = "FROM Usuario";

            queryDinamico = queryDinamico + " WHERE Nombre LIKE :nombre";
            queryDinamico = queryDinamico + " AND  ApellidoPaterno = :apaterno";
            queryDinamico = queryDinamico + " AND ApellidoMaterno = :amaterno";

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery(queryDinamico, Usuario.class);
            queryUsuario.setParameter("nombre", "%" + usuario.getNombre() + "%");
            queryUsuario.setParameter("apaterno", "%" + usuario.getApellidoPaterno() + "%");
            queryUsuario.setParameter("amaterno", "%" + usuario.getApellidoMaterno() + "%");
            
            List<Usuario> usuarios = queryUsuario.getResultList();
            result.object = usuarios;

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}

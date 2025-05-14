package com.digis01.FNolascoProgramacionNCapas.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int IdUsuario;

    @Column(name = "username")
    private String UserName;

    @Column(name = "nombre")
    private String Nombre;

    @Column(name = "apellidopaterno")
    private String ApellidoPaterno;

    @Column(name = "email")
    private String Email;

    @Column(name = "sexo")
    private String Sexo;

    @Column(name = "telefono")
    private String Telefono;

    @Column(name = "celular")
    private String Celular;

    @Column(name = "curp")
    private String Curp;

    @Column(name = "apellidomaterno")
    private String ApellidoMaterno;

    @Column(name = "password")
    private String Password;

    @ManyToOne
    @JoinColumn(name = "idroll")
    public Roll Roll;

    @Column(name = "fechanacimiento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento;

    @Lob
    @Column(name = "imagen")
    private String Imagen;

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public Usuario() {
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public String getUserName() {
        return UserName;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public String getEmail() {
        return Email;
    }

    public String getSexo() {
        return Sexo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getCelular() {
        return Celular;
    }

    public String getCurp() {
        return Curp;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public String getPassword() {
        return Password;
    }

    public Roll getRoll() {
        return Roll;
    }

    public void setRoll(Roll Roll) {
        this.Roll = Roll;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public void setCurp(String Curp) {
        this.Curp = Curp;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

}

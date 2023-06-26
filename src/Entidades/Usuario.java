/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author Hansel
 */
public class Usuario {
    String codigo;
    String nombre;
    String segundoNombre;
    String apellidoP;
    String apellidoM;
    String correo;
    String telefono;
    String username;
    String password;
    String cargo;
    String sucursal;
    public Usuario (){
    
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombres(String nombre,String segundoNombre) {
        this.nombre = nombre;
         this.segundoNombre = segundoNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidos(String apellidoP,String apellidoM) {
        this.apellidoP = apellidoP;
         this.apellidoM = apellidoM;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    
    
}

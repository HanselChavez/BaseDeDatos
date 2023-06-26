/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventas;

import Entidades.Usuario;
import Pantallas.IniciarSesion;
import Utilidades.Servicios;
/**
 *
 * @author chave
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static Usuario user ;
    public static void main(String[] args) {
        Servicios.cargarValores();
        IniciarSesion login = new IniciarSesion();  
        login.setVisible(true);
     
                
    }
    public static void setUsuario(Usuario user)
    {
        Main.user = user;
    }
    public static Usuario getUsuario(){
        return Main.user;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.sql.DriverManager;

import java.sql.SQLException;


/**
 *
 * @author Hansel Chavez
 */
public class Conexion {
    private static java.sql.Connection conexion ;

            
    private static final String url = 
            "jdbc:sqlserver://localhost:1433;databaseName=Ventas";
    private static final String usuario = "sa";
    private static final String contraseña = "CARLOS3231234";
    public static java.sql.Connection getConexion(){
         
        try {          
            //Solicitando al cargador de clases que busque y cargue  la clase 
            //SQLServerDriver 
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //Establecer y obtener la conexion
            conexion= DriverManager.getConnection(url,usuario,contraseña);     
            return conexion;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error: "+ ex.getMessage());
        }
        return null;
    }    
}

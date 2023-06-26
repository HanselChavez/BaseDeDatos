/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author chave
 */
public class Arrays {
    
    
    public static String [] arrCliente = new String[6];
    public static Object [] arrProducto = new Object [11] ;
    //apublic static String [] ArrDireccion= new String [6];
    
    public static void llenarArrayCliente(ResultSet rs){
        try {
            rs.next();
            arrCliente[0] = rs.getString("codigo");
            arrCliente[1] = rs.getString("tipo");
            arrCliente[2] = rs.getString("nombres");
            arrCliente[3] = rs.getString("apellidos");
            arrCliente[4] = rs.getString("telefono");
            arrCliente[5] = rs.getString("canal");
        } catch (SQLException ex) {
            System.out.println("Query vacio llenarArrayCliente");
        }        
    }/*
    public static void llenarArrayDir(ResultSet rs){
        try {
            rs.next();
            ArrDireccion[0] = rs.getString("id");
            ArrDireccion[1] = rs.getString("departamento");
            ArrDireccion[2] = rs.getString("provincia");
            ArrDireccion[3] = rs.getString("distrito");
            ArrDireccion[4] = rs.getString("direccion");
            ArrDireccion[5] = rs.getString("referencia");
        } catch (SQLException ex) {
            System.out.println("Query vacio llenarArrayDir");
        }        
    }*/
    static void llenarArrayProducto(ResultSet rs) {
        try {
            rs.next();
            arrProducto[0] = rs.getString("id");
            arrProducto[1] = rs.getString("nombre");
            arrProducto[2] = rs.getString("descripcion");
            arrProducto[3] = rs.getString("precio");
            arrProducto[4] = rs.getString("idsubCate");
            arrProducto[5] = rs.getString("nombreSub");
            arrProducto[6] = rs.getString("idcate");
            arrProducto[7] = rs.getString("nombreCate");
            arrProducto[8] = rs.getBytes("foto");
            arrProducto[9] = rs.getString("marca");
            arrProducto[10] = rs.getString("cantidad");
        } catch (SQLException ex) {
            System.out.println("Query vacio llenarArrayProducto");
        }   

    }
    
}

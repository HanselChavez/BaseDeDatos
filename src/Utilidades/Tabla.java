/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import RSMaterialComponent.RSTableMetroCustom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author chave
 */
public class Tabla {
     public static int llenarTablaClientes(ResultSet rs,RSTableMetroCustom tabla){
         try {
             int contador = 0;
             
             DefaultTableModel modeloTabla = (DefaultTableModel) tabla.getModel();
             while(modeloTabla.getRowCount()>0){
                 modeloTabla.removeRow(0);
             }
             String[] fila= new String[6];
             while(rs.next()){
                 
                 fila[0] = rs.getString("codigo");
                 fila[1] = rs.getString("tipo");
                 fila[2] = rs.getString("nombres");
                 fila[3] = rs.getString("apellidos");
                 fila[4] = rs.getString("telefono");
                 fila[5] = rs.getString("canal");
                 contador++;
                 modeloTabla.addRow(fila);
                 
             }
             return contador;
         } catch (SQLException ex) {
             System.out.println("No se pudo llenar");
         }
        return 0;
    } 

    public static void llenarTablaDirQuery(ResultSet rs, RSTableMetroCustom tablaDir
    ,List<String[]>list) {
        try {
         
            DefaultTableModel modeloTabla = (DefaultTableModel) tablaDir.getModel();
            while(modeloTabla.getRowCount()>0){
                modeloTabla.removeRow(0);
            }
            String[] fila= new String[6];
            while(rs.next()){
                 
                fila[0] =  rs.getString("id");
                fila[1] = rs.getString("departamento");
                fila[2] = rs.getString("provincia");
                fila[3] = rs.getString("distrito");
                fila[4] =  rs.getString("direccion");
                fila[5] = rs.getString("referencia");     
                list.add(fila);
                modeloTabla.addRow(fila);   
            }
           
         } catch (SQLException ex) {
             System.out.println("No se pudo llenar tabla dir");
         }
      

    }

    static void llenarTablaProductos(ResultSet rs, RSTableMetroCustom tabla) {
        try {
         
            DefaultTableModel modeloTabla = (DefaultTableModel) tabla.getModel();
            while(modeloTabla.getRowCount()>0){
                modeloTabla.removeRow(0);
            }
            String[] fila= new String[6];
            while(rs.next()){                 
                fila[0] =  rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("descripcion");
                fila[3] = rs.getString("nombreSub");
                fila[4] = rs.getString("cantidad");
                fila[5] =  rs.getString("precio");       
                modeloTabla.addRow(fila);   
            }
           
         } catch (SQLException ex) {
             System.out.println("No se pudo llenar tabla dir");
         }

    }

 
}

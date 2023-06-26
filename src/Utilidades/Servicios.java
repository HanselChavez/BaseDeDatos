/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import Config.Conexion;
import Entidades.Usuario;
import RSMaterialComponent.RSComboBoxMaterial;
import RSMaterialComponent.RSTableMetroCustom;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ventas.Main;

/**
 *
 * @author Hansel Chavez
 */
public class Servicios {

  
    private static final Connection conexion = Conexion.getConexion();    
    private static String sqlQuery ="";
    private static Statement bdSelect ;
    private static PreparedStatement bdUpdate ;
    public static void cargarValores(){
        try {
            bdUpdate = conexion.prepareStatement(sqlQuery);
            bdSelect = conexion.createStatement();
        } catch (SQLException ex) {
            System.out.println("No se pudo cargar los valores");
        }
    
    }
    public static boolean verificarTrabajador(String dni){
        try {
            sqlQuery = "select COUNT(idTrabajador) cantidad from Trabajador"
                    + " where idTrabajador = '"+dni+"'";
            ResultSet rs = bdSelect.executeQuery(sqlQuery);
            rs.next();
            return rs.getInt("cantidad")==1;     
        } catch (SQLException ex) {
            
        }
        return false;
    }
    public static int generarCodigo(){
        try {
            sqlQuery= "SELECT MAX(CAST(SUBSTRING(idProducto, 3, LEN(idProducto)"
                    + " - 2) AS INT)) AS Mayor FROM Producto";
            ResultSet rs = bdSelect.executeQuery(sqlQuery);
            rs.next();
            return rs.getInt("Mayor")+1;
        } catch (SQLException ex) {
            System.out.println("no se pudo generar codigo");
        }
        return 0;
    }
    //Cargar datos de una consulta a un Objeto Usuario
    private static Usuario cargarDatosSesion(String username)
            throws SQLException {
        ResultSet result =  Servicios.getUsuario(username);
        Usuario user = new Usuario();
        if(result.next()){
            inicializarUsuario(result, user);
        }       
        return user;
    }
    private static ResultSet getDireccion(String doc,String idDir){
        try {
            sqlQuery = "select idDireccion id,departamento,provincia,distrito"
                    + ",direccion,referencia from Cliente_Direccion "
                    + "where idCliente = '"+doc+"'";
            if(!"".equals(idDir))
                sqlQuery+= " And idDireccion = '"+idDir+"'";
            return bdSelect.executeQuery(sqlQuery);
        } catch (SQLException ex) {
            System.out.println("Error query direccion");
        }
        return null;
    }
     //Obtener datos de Usuario por su username
    private static ResultSet getUsuario(String username){   
        try {
            sqlQuery = "select t.idTrabajador as [codigo], t.nombreTrabajador"
                    + " as [nombres], t.apellidoPaterno as [apellidoP], "
                    + "t.apellidoMaterno as [apellidoM], t.Telefono,u.username"
                    + ",u.correo, u.contraseña contra from Trabajador t "
                    + "inner join Usuario u on (t.idTrabajador=u.idTrabajador)"
                    + " where username = '"+username+"'";
            ResultSet rs = bdSelect.executeQuery(sqlQuery);
            return rs;
        } catch (SQLException ex) {
            
        }
        return null;
    }
     //Autenticar contraseña de un usuario
    public static boolean validarUsuario(String username, String password) { 
        try {
            ResultSet result =  Servicios.getUsuario(username);
            result.next();
            boolean resultado = result.getString("contra")
                    .equals(password);
            if(resultado )
                Main.setUsuario(cargarDatosSesion(username));
            return resultado;    
        } catch (SQLException ex) {
          
        }
        return false;
    }
    
    public static boolean verificarCampo(String campo,String valor) {
        try {
            sqlQuery = "select count("+campo+") cantidad"
                    + " from Usuario where "+campo+"='"+valor+"'";
            ResultSet rs = bdSelect.executeQuery(sqlQuery);
            rs.next();
            return rs.getInt("cantidad")==1;                
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        return false;
    }
    private static ResultSet getProducto( String cod) {
        try {
             sqlQuery = "select p.idProducto id, p.marcaProducto marca,st.cantidad"
                     + ",p.nombreProducto nombre,p.descripcion,p.precioUnitario"
                     + " precio, p.idSubCategoria idsubCate,s.nomSubCat nombreSub"
                     + ",c.idCategoria idcate, c.nombreCategoria nombreCate,"
                     + " p.foto from Producto p inner join SubCategoria s on"
                     + " (p.idSubCategoria = s.idSubCategoria) inner join "
                     + "Categoria c on (c.idCategoria = s.idCategoria) inner join "
                    + "Stock st on( st.idProducto = p.idProducto) inner join "
                    + "Almacen al on( st.idAlmacen = al.idAlmacen)  "
                    + "where p.idProducto ='"+cod+"' ";    
            ResultSet rs = bdSelect.executeQuery(sqlQuery);
            return rs;
        } catch (SQLException ex) {
            System.out.println("No se pudo obtener el producto con el codigo: "
                    +cod);
        }
        return null;
    }
     private static ResultSet getProductos(String cate,String Alm ,String cod) {
        try {
            sqlQuery = "select p.idProducto id,p.marcaProducto marca,"
                    + " p.nombreProducto nombre,p.descripcion,p.precioUnitario"
                    + " precio, p.idSubCategoria idsubCate,s.nomSubCat nombreSub"
                    + ",c.idCategoria idcate,c.nombreCategoria nombreCate,"
                    + "st.cantidad "
                    + ", p.foto from Producto p inner join SubCategoria s on  "
                    + "(	p.idSubCategoria = s.idSubCategoria) inner join "
                    + "Categoria c on (	c.idCategoria = s.idCategoria)inner join "
                    + "Stock st on( st.idProducto = p.idProducto) inner join "
                    + "Almacen al on( st.idAlmacen = al.idAlmacen) "
                    + "where (s.idCategoria ='"+cate+"' AND st.idAlmacen ='"+
                    Alm +"') ";            
            if(!"".equals(cod)){
                sqlQuery+="AND (p.nombreProducto "
                + "LIke('"+cod+"%') OR p.nombreProducto LIKE('%"+cod+"%'))" ;
            } 
            ResultSet rs = bdSelect.executeQuery(sqlQuery);
            return rs;
        } catch (SQLException ex) {
            System.out.println("No se pudo obtener los productos");
        }
        return null;
    }

    public static ResultSet getCliente(String cod){
        try {
            sqlQuery = "select c.idCliente codigo,c.tipoDocumento tipo,"
                    + "c.nombreCliente nombres, c.apellidoCliente apellidos"
                    + ", c.telefonoCliente telefono, cc.nombreCanal canal "
                    + "from Cliente c inner join CanalContacto cc on "
                    + "(c.idCanalContacto = cc.idCanalContacto) "
                    + "where c.idCliente = '"+cod+"'";         
            ResultSet rs = bdSelect.executeQuery(sqlQuery);
            return rs;
        } catch (SQLException ex) {
            System.out.println("No se pudo obtener el cliente con el codigo: "
                    +cod);
        }
        return null;
    }
    public static ResultSet getClientes(String tipo,String buscar){
        try {
            sqlQuery = "select c.idCliente codigo,c.tipoDocumento tipo,"
                    + "c.nombreCliente nombres, c.apellidoCliente apellidos"
                    + ", c.telefonoCliente telefono, cc.nombreCanal canal "
                    + "from Cliente c inner join CanalContacto cc on "
                    + "(c.idCanalContacto = cc.idCanalContacto) "
                    + "where c.tipoDocumento = '"+tipo+ "'";
            if(!buscar.equals(""))
                sqlQuery+=  " AND (c.idCliente  LIKE '"+buscar+"%' "
                        + "OR c.idCliente LIKE '%"+buscar+"%')";
            ResultSet rs = bdSelect.executeQuery(sqlQuery);
            return rs;
        } catch (SQLException ex) {
            System.out.println("No se pudo ejecutar query getClientes");
        }
        return null;
    }
   
    public static void agregarCliente(String[] insertar) {
        try {
            sqlQuery = "insert into Cliente(idCliente,tipoDocumento"
                    + ",nombreCliente,apellidoCliente,telefonoCliente"
                    + ",idCanalContacto,usuCreador,usuModifica,fechaCreador"
                    + ",fechaModifica) values('"+insertar[0]+"','"+insertar[1]
                    + "','"+insertar[2]+ "','"+insertar[3]+ "','"+insertar[4]
                    + "','"+insertar[5]+ "','sa','sa',GETDATE(),GETDATE())" ;
            bdUpdate = conexion.prepareStatement(sqlQuery);
            bdUpdate.execute();
        } catch (SQLException ex) {
            System.out.println("No se pudo insertar el cliente");
        }
    }
    public static void agregarDirQuery(String[] nuevaDir,String doc) {
        try {
            sqlQuery = "insert into Cliente_Direccion(idCliente,idDireccion"
                    + ",departamento,provincia,distrito,direccion,referencia"
                    + ",usuCreador,fechaCreador,usuModifica,fechaModifica) "
                    + "values('"+doc+"','"+nuevaDir[0]+"','"+nuevaDir[1]+"','"
                    + nuevaDir[2]+"','"+nuevaDir[3]+"','"+nuevaDir[4]+"','"
                    +nuevaDir[5]+"','sa',GETDATE(),'sa',GETDATE())";
            bdUpdate= conexion.prepareStatement(sqlQuery);
            bdUpdate.execute();
        } catch (SQLException ex) {
            System.out.println("no se pudo insertar dir");
        }
    }
    
    public static void agregarProducto(String[] nuevPro, byte[] bytes) {
        try {
            sqlQuery = "insert into Producto (idProducto,nombreProducto "
                    + ",descripcion, precioUnitario,idSubCategoria,idProveedor"
                    + ",marcaProducto,usuCreador,usuModifica,fechaCreador,"
                    + "fechaModifica,foto) values('"+nuevPro[0]+"','"+nuevPro[1]
                    +"','"+nuevPro[2]+"','"+nuevPro[3]+"','"+nuevPro[4]+"','"+
                    nuevPro[5]+"','"+nuevPro[6]+"','"+nuevPro[7]+"')";
            bdUpdate= conexion.prepareStatement(sqlQuery);
            bdUpdate.setBytes(1,bytes);
            bdUpdate.execute();
        } catch (SQLException ex) {
            System.out.println("no se pudo agregar producto");
        }

    }
    public static void eliminarDir(String cod) {
        try {
            sqlQuery = "delete from Cliente_Direccion where idDireccion"
                    + " ='"+cod+"'";
            bdUpdate= conexion.prepareStatement(sqlQuery);
            bdUpdate.execute();
        } catch (SQLException ex) {
            System.out.println("Error delete dir");
        }
    }
    public static void actualizarCliente(String docA, String[] list) {
        try {
            sqlQuery = "Update Cliente set idCliente = '"+list[0]+"',"
                    + "tipoDocumento='"+list[1]+"',nombreCliente='"+list[2]
                    +"',"+"apellidoCliente='"+list[3]+"',telefonoCliente='"
                    +list[4]+"',idCanalContacto ='"+list[5]+"' "
                    +"where idCliente = '"+docA+"'";
            
            bdUpdate= conexion.prepareStatement(sqlQuery);
            bdUpdate.execute();
        } catch (SQLException ex) {
            System.out.println("No se pudo actualizar");
        }
    }
    public static void actualizarProducto(String[] actualizar,byte [] foto) {
        try {      
            sqlQuery = "update Producto set nombreProducto ='"+actualizar[1]+"',"
                    + " descripcion = '"+actualizar[2]+"', precioUnitario = '"
                    +actualizar[3] +"',idSubCategoria = '"+actualizar[4]+"',"
                    + "foto =? where idProducto = '"+actualizar[0]+"'";          
            bdUpdate= conexion.prepareStatement(sqlQuery);            
            bdUpdate.setBytes(1,foto);
            bdUpdate.execute();
            sqlQuery = "update Stock set cantidad= "+actualizar[7]+
                    " where idAlamcen ='"+actualizar[8]
                    +"' AND idProducto = '"+actualizar[0]+"'";
            bdUpdate= conexion.prepareStatement(sqlQuery);  
            bdUpdate.execute();
        } catch (SQLException ex) {
            System.out.println("Error actualizar producto cod:" +actualizar[0]);
        }
    }
    public static void actualizarDir(String doc, String[] dir) {
        try {
            sqlQuery = "Update Cliente_Direccion set departamento = '"+dir[1]
                    +"',provincia='"+dir[2]+"',distrito='"+dir[3]+"',"+
                    "direccion='"+dir[4]+"',referencia='"+dir[5]+"' "
                    +"where idCliente = '"+doc+"' AND idDireccion= '"+dir[0]+"'";
            
            bdUpdate= conexion.prepareStatement(sqlQuery);
            bdUpdate.execute();
        } catch (SQLException ex) {
            System.out.println("No se pudo editar la direccion query");
        }

    }
    public static int listarClientes(RSTableMetroCustom tabla ,String tipo 
            , String buscar){
        ResultSet rs = getClientes(tipo,buscar);
        return Tabla.llenarTablaClientes(rs,tabla);    
    }    
    public static void listarProductos(RSTableMetroCustom tabla
            , String Categoria,String Almacen, String buscar) {
        ResultSet rs = getProductos(Categoria,Almacen ,buscar);
        Tabla.llenarTablaProductos(rs,tabla);   
    }
    public static void listarDireccionQuery(RSTableMetroCustom tablaDir
            , String doc,List<String[]>list) {
        ResultSet rs = getDireccion(doc,"");
        Tabla.llenarTablaDirQuery(rs,tablaDir,list);

    }   /* 
    public static void llenarArrayDireccion(String doc, String idDir) {
        ResultSet rs = getDireccion(doc,idDir);
        Arrays.llenarArrayDir(rs);
    }*/
    public static void llenarArrayProducto(String cod) {
        ResultSet rs = getProducto(cod);
        Arrays.llenarArrayProducto(rs);
    }
    public static void llenarArrayCliente(String doc) {
        ResultSet rs = getCliente(doc);
        Arrays.llenarArrayCliente(rs);
    }
    public static void inicializarUsuario(ResultSet result, Usuario user)   {
        try {
            String Id = result.getString("codigo");
            String [] nombres = result.getString("nombres").split(" ");
            String nombre1 = nombres[0];
            String nombre2="";
            if(nombres.length>1)
                nombre2 = nombres[1];
            String apellidoP = result.getString("apellidoP");
            String apellidoM = result.getString("apellidoM");      
            String telefono = result.getString("Telefono");
            String nombreUsuario =  result.getString("username");
            String correo = result.getString("correo");
            String contraseña =  result.getString("contra");         
            user.setCodigo(Id);
            user.setNombres(nombre1,nombre2);
            user.setApellidos(apellidoP,apellidoM);
            user.setTelefono(telefono);
            user.setUsername(nombreUsuario);      
            user.setCorreo(correo);       
            user.setPassword(contraseña);

        } catch (SQLException ex) {
       
        }
    }

    public static void agregarDireccion(String doc, List<String[]> listaDirec) {
        listaDirec.forEach((strings) -> {          
               agregarDirQuery(strings,doc);           
        });
    }
    
    public static void llenarCombobox(String idCate, RSComboBoxMaterial cbSubCategoria) {
        try {
            ResultSet rs = getSubCategorias(idCate);
            while(rs.next()){
                String subCat = rs.getString("nomSubCat");
                cbSubCategoria.addItem(subCat);
            }
        } catch (SQLException ex) {
            System.out.println("No se pudo llenar subcategorias");
        }
    }

    private static ResultSet getSubCategorias(String idCate) {
         try {
            sqlQuery = "select s.nomSubCat from Categoria c inner join"
                    + " SubCategoria s on (c.idCategoria = s.idCategoria) "
                    + "where c.idCategoria = '"+idCate+"'";
       
            return bdSelect.executeQuery(sqlQuery);
        } catch (SQLException ex) {
            System.out.println("Error query subCategorias");
        }
        return null;
    }



    public static String getIdSubCate(String nombre) {
        try {
            sqlQuery = "select idSubCategoria id from SubCategoria where "
                    + "nomSubCat = '"+nombre+"'";
            ResultSet rs = bdSelect.executeQuery(sqlQuery);
            rs.next();
            return rs.getString("id");                
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        
        return null;

    }


   


   

   

 



 

  
}

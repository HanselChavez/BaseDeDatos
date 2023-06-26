/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Hansel Chavez
 */
public class Foto {

    public static void subirFotoArchivo(JLabel lblfoto,JPanel padre) 
            throws IOException{
        // Crear el JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter
        ("JPG","jpg");
        fileChooser.setFileFilter(filtro);
        // Mostrar el cuadro de diálogo para seleccionar la imagen
        int seleccion = fileChooser.showOpenDialog(padre);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado
            File archivo = fileChooser.getSelectedFile();
            // Cargar la imagen
            BufferedImage imagen = ImageIO.read(archivo);  
            // Redimensionar la imagen al tamaño del Label
            if (imagen != null) {               
                // Establecer la imagen redimensionada en el JLabel
                lblfoto.setIcon(redimensionarImagen(imagen, lblfoto));
            }
        } 
    }
    private static ImageIcon redimensionarImagen(BufferedImage imagen, JLabel lblfoto) {
        Image imagenRedimensionada = imagen.getScaledInstance(lblfoto.getWidth()
                , lblfoto.getHeight(), Image.SCALE_SMOOTH);
        
        return new ImageIcon(imagenRedimensionada);
    }
    public static byte[] obtenerFotoLabel(JLabel lblfoto) throws IOException{          
            
            // Crear un objeto BufferedImage para almacenar la imagen del JLabel
            BufferedImage image = new BufferedImage(lblfoto.getWidth()
                    , lblfoto.getHeight(), BufferedImage.TYPE_INT_RGB);
            
            // Dibujar el contenido del JLabel en el BufferedImage
            lblfoto.paint(image.getGraphics());
            
            // Crear un objeto ByteArrayOutputStream para almacenar los bytes de la imagen
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            // Escribir la imagen en el flujo de salida ByteArrayOutputStream
            ImageIO.write(image, "jpg", baos);
            
            // Obtener los bytes de la imagen
            byte[] imageBytes = baos.toByteArray();            
            baos.close();
        
            if(imageBytes.length<=1)
                return null;
      
            return imageBytes;
    }
    public static void cargarFotoLabel(JLabel lblfoto , byte [] imagen)
            throws IOException{
        if(imagen!=null){        
            BufferedImage image = convertByteArrayToImage(imagen);
            lblfoto.setIcon(redimensionarImagen(image, lblfoto));       
        }else{
            lblfoto.setIcon(null);
        }
       
    }
    private static BufferedImage convertByteArrayToImage(byte[] imageBytes) 
             throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bais);
    }        
}
   
    

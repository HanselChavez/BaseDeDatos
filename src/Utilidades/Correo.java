/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;



import javax.mail.Authenticator;

import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Kelvin Vasquez
 */
public class Correo {
    
    private static final int MIN_CODIGO = 100000;
    private static final int MAX_CODIGO = 999999;
    private static final Random random = new Random();

    private static int generarCodigoAleatorio() {
        return MIN_CODIGO + random.nextInt(MAX_CODIGO - MIN_CODIGO + 1);
    }
    public static String enviarCorreoElectronico(String destinatario ) 
            throws MessagingException  {
        String codigo =String.valueOf(generarCodigoAleatorio());
     
        // Configurar las propiedades del servidor de correo
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.user","servicioaguapotable970@gmail.com");
        properties.setProperty("mail.smtp.auth", "true");

        // Configurar las credenciales del remitente
        final String remitente = "servicioaguapotable970@gmail.com";
        final String password = "pwnjobfgqzddbfaq";

        // Crear una instancia de Session utilizando properties 
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            //sobrescribe dentro de la instancia Authenticator 
            //para proporcionar las credenciales del remitente.
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        // Crear el mensaje de correo
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remitente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress
                .parse(destinatario));
        //Establecer el asunto
        message.setSubject("Verificación de correo");
        //se establece el cuerpo
        message.setText("Tu código de verificación es: " + codigo);
        // Enviar el mensaje de correo
        Transport.send(message);
        System.out.println(codigo);
        //ServicioDeAgua.mensaje.cargarDatos("Verificar Correro"
         //       ,"Codigo de Verificación enviado correctamente.", 1);           
       
        return codigo;
    }  

}

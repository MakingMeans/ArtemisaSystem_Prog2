package co.edu.unbosque.model;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * La clase EmailSender se encarga de enviar mensajes de correo electrónico.
 * 
 * Esta clase proporciona métodos para enviar correos electrónicos utilizando SMTP (Protocolo Simple de Transferencia de Correo) a través de
 * una cuenta de Gmail.
 * 
 * @author DavidG
 * @version 1.0
 */
public class EmailSender {

    private static final String EMAIL_FROM = "bbitatyx@gmail.com";
    private static final String EMAIL_TO = "";
    private static final String APP_PASSWORD = "suix bfmh eynv gjri";

    /**
     * Método principal para probar la funcionalidad de envío de correo electrónico.
     * 
     * Este método crea y envía un mensaje de correo electrónico utilizando los parámetros configurados.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        Message message = new MimeMessage(getEmailSession());
        try {
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
            message.setSubject("Registro plataforma Artemisa");
            message.setText("Su registro en la plataforma ha sido efectivo.");
            Transport.send(message);
        } catch (Exception e) {
            // TODO: manejar excepción
        }

    }

    /**
     * Obtiene la sesión de correo electrónico.
     * 
     * Este método configura la sesión de correo electrónico utilizando las propiedades de Gmail y un autenticador
     * con el correo electrónico y la contraseña de la aplicación especificados.
     * 
     * @return La sesión de correo electrónico configurada.
     */
    private static Session getEmailSession() {
        return Session.getInstance(getGmailProperties(), new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });
    }

    /**
     * Obtiene las propiedades de Gmail para configurar la sesión de correo electrónico.
     * 
     * Este método establece propiedades como autenticación, habilitación de TLS, host, puerto y confianza en SSL.
     * 
     * @return Objeto Properties que contiene las propiedades de Gmail.
     */
    private static Properties getGmailProperties() {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return prop;

    }

    /**
     * Obtiene la dirección del remitente del correo electrónico.
     * 
     * @return La dirección del remitente del correo electrónico.
     */
    public static String getEmailFrom() {
        return EMAIL_FROM;
    }

    /**
     * Obtiene la dirección del destinatario del correo electrónico.
     * 
     * @return La dirección del destinatario del correo electrónico.
     */
    public static String getEmailTo() {
        return EMAIL_TO;
    }
}

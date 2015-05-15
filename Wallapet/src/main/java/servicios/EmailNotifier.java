/*
 * Nombre: EmailNotifier.java
 * Version: 1.0
 * Autor: Sergio Soro.
 * Fecha 7-5-2015
 * Descripcion: Este fichero implementa la clase que se encarga de crear y enviar
 *              un correo electronico con un documento adjunto.
 * Copyright (C) 2015 Hyena Technologies
 */

package servicios;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailNotifier{

    private static final Logger logger =
            Logger.getLogger(EmailNotifier.class.getName());
    private final String USERNAME = "wallapet.sa@gmail.com";
    private final String PASSWORD = "";
    private final Properties props = new Properties() {{
        put("mail.smtp.auth", "true");
        put("mail.smtp.starttls.enable", "true");
        put("mail.smtp.host", "smtp.gmail.com");
        put("mail.smtp.port", "587");
    }};
    private String destEmail;

    /**
     * Pre: destEmail contiene un correo valido.
     * Post:
     */
    public EmailNotifier(String destEmail) {
        this.destEmail = destEmail;
    }


    /**
     * Pre: ruta es un path valido.
     * Post: Crea un anuncio y lo envia a destEmail.
     */
    public void sendNotification(String title, String description, String ruta) {
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {

            // Crea el mensaje.
            Message message = new MimeMessage(session);

            // Quien lo envia
            message.setFrom(new InternetAddress(USERNAME));

            // Destinatario.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destEmail));

            // Titulo
            message.setSubject(title);

            // Crea el cuerpo del mensaje
            BodyPart messageBodyPart = new MimeBodyPart();

            // add el texto al cuerpo del mensaje
            messageBodyPart.setText(description);

            // crea un mensaje multiparte
            Multipart multipart = new MimeMultipart();

            // Le añade la parte de texto al mensaje
            multipart.addBodyPart(messageBodyPart);

            // add el pdf
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(ruta);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(ruta);
            multipart.addBodyPart(messageBodyPart);

            // add al mensaje las partes
            message.setContent(multipart);

            // Envia el mensaje
            Transport.send(message);

            logger.log(Level.INFO, "Notification sent succesfully to " + destEmail);
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
    }


}

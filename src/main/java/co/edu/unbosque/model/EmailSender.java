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
 * 
 * Email sender.
 * 
 * @author DavidG
 *
 */
public class EmailSender {

	private static final String EMAIL_FROM = "bbitatyx@gmail.com";
	private static final String EMAIL_TO = "";
	private static final String APP_PASSWORD = "suix bfmh eynv gjri";

	public static void main(String[] args) {

		Message message = new MimeMessage(getEmailSession());
		try {
			message.setFrom(new InternetAddress(EMAIL_FROM));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
			message.setSubject("Registro plataforma Artemisa");
			message.setText("Su registro en la plataforma ha sido efectivo.");
			Transport.send(message);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private static Session getEmailSession() {
		return Session.getInstance(getGmailProperties(), new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
			}
		});
	}

	private static Properties getGmailProperties() {

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		return prop;

	}

	public static String getEmailFrom() {
		return EMAIL_FROM;
	}

	public static String getEmailTo() {
		return EMAIL_TO;
	}

}
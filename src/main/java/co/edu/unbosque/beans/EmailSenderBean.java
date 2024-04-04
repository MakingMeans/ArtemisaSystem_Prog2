package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.Properties;

import co.edu.unbosque.model.Email;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Named("emailSenderBean")
@SessionScoped
public class EmailSenderBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Email email = new Email();

	public Email getEmail() {
		return email;
	}

	public void sendEmail() {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");

		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("bbitatyx@gmail.com", "suix bfmh eynv gjri");
			}

		});
		try {
			System.out.println(email.getRecipient());
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("bbitatyx@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getRecipient()));
			message.setSubject(email.getSubject());
			message.setText(email.getContent());
			Transport.send(message);
			System.out.println("Enviado");
		} catch (MessagingException e) {
			System.out.println("No Enviado");
			e.printStackTrace();
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

}
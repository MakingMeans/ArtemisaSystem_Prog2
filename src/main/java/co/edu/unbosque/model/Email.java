package co.edu.unbosque.model;

/**
 * La clase Email representa un mensaje de correo electrónico.
 * 
 * Esta clase permite crear, establecer y obtener propiedades de un mensaje de correo electrónico,
 * tales como el destinatario, el asunto y el contenido.
 * 
 * @author DavidG
 * @version 1.0
 */
public class Email {

    private String recipient;
    private String subject;
    private String content;

    /**
     * Constructor predeterminado para la clase Email.
     * 
     * Inicializa el asunto y el contenido del correo electrónico con valores predeterminados.
     */
    public Email() {
        subject = "Registro plataforma Artemisa";
        content = "Su registro en la plataforma ha sido efectivo.";
    }

    /**
     * Constructor para la clase Email.
     * 
     * @param recipient El destinatario del correo electrónico.
     * @param subject El asunto del correo electrónico.
     * @param content El contenido del correo electrónico.
     */
    public Email(String recipient, String subject, String content) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }

    /**
     * Obtiene el destinatario del correo electrónico.
     * 
     * @return El destinatario del correo electrónico.
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Establece el destinatario del correo electrónico.
     * 
     * @param recipient El destinatario del correo electrónico.
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Obtiene el asunto del correo electrónico.
     * 
     * @return El asunto del correo electrónico.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Establece el asunto del correo electrónico.
     * 
     * @param subject El asunto del correo electrónico.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Obtiene el contenido del correo electrónico.
     * 
     * @return El contenido del correo electrónico.
     */
    public String getContent() {
        return content;
    }

    /**
     * Establece el contenido del correo electrónico.
     * 
     * @param content El contenido del correo electrónico.
     */
    public void setContent(String content) {
        this.content = content;
    }
}

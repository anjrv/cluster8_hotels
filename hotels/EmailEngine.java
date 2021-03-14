package hotels;

// The imports and their methods are marked as cannot resolve
// because their jar is compressed within hoteldeps.jar
// and is no longer detected automatically.
//
// This was done to reduce the amount of files present.
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Authors: Einar Jónsson Eydís Sylvía Einarsdóttir Jaan Jaerving Snorri Steinn
 * Stefánsson Thors
 */
class EmailEngine {
    // Dummy gmail account information for this project, do not steal!
    private static final String FROM = "cluster8.hotels.noreply@gmail.com";
    private static final String PASSWORD = "c8hbv401";

    /**
     * Sends a confirmation email from the project email account FROM.
     * 
     * @param to the email address to send to
     * @param sub the subject of the message
     * @param msg the text body of the message
     */
    static void send(String to, String sub, String msg) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            Transport.send(message);
            System.out.println("Confirmation email has been sent.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

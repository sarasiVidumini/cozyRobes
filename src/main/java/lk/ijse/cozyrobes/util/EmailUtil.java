package lk.ijse.cozyrobes.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;


public class EmailUtil {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String USERNAME = "sarasividumini13@gmail.com";
    private static final String PASSWORD = "crbt phsj wasa pfjq";

    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }

    // Send a simple email (no attachment)
    public static boolean sendSimpleMail(String to, String subject, String body) {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Send an email with an attachment
    public static boolean sendMailWithAttachment(String to, String subject, String body, String filePath) {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Body part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);

            // Attachment part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(source.getName());

            // Combine parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }}

}


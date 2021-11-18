package montp.tools;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.Properties;

public abstract class EMailer {

    private String server;
    private String user;
    private String password;
    private int port;

    public EMailer() {
    }

    public EMailer(String server, String user, String password, int port) {
        this.server = server;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    public abstract String getFrom();

    public String getUser() { return user; }

    public void send(String to, String subject, String content)
            throws AddressException, MessagingException {
        send(to, subject, content, true, null);
    }

    public void send(String to, String subject, String content,
            boolean html, String[] attachements)
            throws AddressException, MessagingException {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", server);
        props.put("mail.smtp.port", Integer.toString(port));
        if ((user != null) && (user.length() > 0)) {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.EnableSSL.enable", "true");
            props.put("mail.smtp.ssl.trust", "*"); 
        }
        Session session = Session.getDefaultInstance(props, null);
        String[] dests = new String[]{to};
        for (String dest : dests) {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(getFrom()));
            message.setSentDate(new Date());
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(dest));
            message.setSubject(subject, "UTF-8");
            if (attachements != null) {
                Multipart multipart = new MimeMultipart();
                BodyPart messageBodyPart = new MimeBodyPart();
                if (html) {
                     messageBodyPart.setHeader("Content-Type", "text/plain; charset=\"utf-8\"");
                    messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
                    messageBodyPart.setContent(content,
                            "text/html; charset=UTF-8");
                } else {
                    messageBodyPart.setContent(content,
                            "text/plain; charset=UTF-8");
                }
                multipart.addBodyPart(messageBodyPart);
                for (String filename : attachements) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(filename);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(new File(filename).getName());
                    multipart.addBodyPart(messageBodyPart);
                }
                message.setContent(multipart);
            } else {
                if (html) {
                     message.setHeader("Content-Type", "text/plain; charset=\"utf-8\"");
                    message.setHeader("Content-Transfer-Encoding", "quoted-printable");
                    message.setContent(content, 
                            "text/html; charset=UTF-8");
                } else {
                    message.setContent(
                            content,
                            "text/plain; charset=UTF-8");
                }
            }
            Transport transport = session.getTransport("smtp");
            if ((user != null) && (user.length() > 0)) {
                transport.connect(user, password);
            } else {
                transport.connect();
            }
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
    }
}

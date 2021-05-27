package fon.njt.redditclone.service;

import fon.njt.redditclone.exceptions.SpringRedditException;
import fon.njt.redditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        final String username = "springreddit011@gmail.com";
        final String password = "RufNath8";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("springreddit011@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(notificationEmail.getRecipient())
            );
            message.setSubject(notificationEmail.getSubject());
            message.setText(mailContentBuilder.build(notificationEmail.getBody()));
            Transport.send(message);
            System.out.println("Done");
            log.info("Activation email sent! Recipient:" + notificationEmail.getRecipient());

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new SpringRedditException("Exception occurred when sending email.");
        }

    }
}

package Server.httpServer.Email;


import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailHandler {
    Email[] emails;


    public void parseData(String data) {
        Gson gson = new Gson();
        emails = gson.fromJson(data, Email[].class);
    }


    public void sentEmail() {

        String host = "smtp.seznam.cz";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "465:1");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        // Get the default Session object.
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("fico.m", "lokomotyva");
                    }
                });


        try {

            for (Email email : emails) {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("fico.m@seznam.cz"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.reciver));
                message.setSubject(email.subject);
                message.setText(email.message);
                Transport.send(message);
                System.out.println("Email odeslán na " + message.getFrom() + " na adresu " + message.getAllRecipients());
            }

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}

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
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.seznam.cz");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("fico.m","lokomotyva");
                    }
                });


        try {
            System.out.println("Snaha poslat Email "+emails.length);
            for (Email email : emails) {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("fico.m@seznam.cz"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.reciver));
                message.setSubject(email.subject);
                message.setText(email.message);
                Transport.send(message);
                System.out.println("Email odeslán na " + message.getFrom() + " na adresu " + message.getAllRecipients());
            }

        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

}

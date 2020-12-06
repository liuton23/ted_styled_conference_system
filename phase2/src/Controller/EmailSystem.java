package Controller;
/*
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailSystem {

    public static void main(String[] args) {
        /*
        String host = "smtp.gmail.com";
        final String user = "csc207Group0757@javatpoint.com";//change accordingly
        final String password = "T2uring07";//change accordingly

        String to = "victoria.april.lambert@gmail.com";//change accordingly

        //Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        //Compose the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Test");
            message.setText("This is simple program of sending email using JavaMail API");

            //send the message
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

         */



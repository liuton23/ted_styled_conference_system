package Gateway;

import Presenter.Presenter;
import UseCases.UserManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//mail imports
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.*;

/**
 * Manages file saving and persistence of information. Also manages sending emails.
 */
public class Gateway {

    private String filepath;
    private File file;
    Session session;
    String email;

    /**
     * Constructs a gateway path with <code>filepath</code>.
     * @param filepath  the name of the file to save and write to. Could also be changed to save file to a different
     *                  directory.
     */
    public Gateway(String filepath){
        this.filepath = filepath;
        this.file = new File(filepath);
        initEmail();
    }

    /**
     * Writes the objects to a file.
     * @param serializing list of objects to be saved.
     * @throws IOException error in writing to file
     */
    public void writeToFile(List<Serializable> serializing) throws IOException {
        OutputStream file = new FileOutputStream(filepath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput objOut = new ObjectOutputStream(buffer);

        for(Object obj: serializing){
            objOut.writeObject(obj);
        }

        objOut.close();
    }

    /**
     * Reads from the save file.
     * @param length the number of objects to load.
     * @return the list of the loaded objects.
     * @throws IOException error in reading file or file not found.
     * @throws ClassNotFoundException class does not exist.
     */
    public ArrayList<Serializable> readFromFile(int length) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(filepath);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput objInput = new ObjectInputStream(buffer);
        ArrayList<Serializable> serialized = new ArrayList<>();

        for(int i = 0; i < length; i++){
            Object adding = objInput.readObject();
            if(adding != null) {
                serialized.add((Serializable) adding);
            }
        }

        return serialized;
    }

    /**
     * Email setup.
     */
    private void initEmail(){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        final String myEmail = "csc207Group0757@gmail.com";
        this.email = myEmail;
        final String myPassword = "T2uring07";
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, myPassword);
            }
        };
        this.session = Session.getDefaultInstance(properties, auth);
    }

    /**
     * Sends emails from <code>email</code> to <code>receiverEmail</code> with subject <code>subject</code> and
     * <code>content</code>.
     * @param receiverEmail <code>String</code> email that it will be sent to.
     * @param subject subject of the email.
     * @param content content of the email.
     * @throws MessagingException invalid email, or error in sending.
     */
    public void sendEmail(String receiverEmail, String subject, String content) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
        message.setSubject(subject);
        message.setContent(content, "text/html; charset=utf-8");
        Transport.send(message);
    }
}

package comm;

import model.Chore;
import model.Room;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Tim on 25/11/2016.
 */
public class CommIn {

    public Map<String, String> check(String user,
                                     String password) { // returns map <subject,email address>
        try {

            //create properties field
            Properties properties;
            Session session;
            Store store;
            properties = new Properties();
            properties.setProperty("mail.host", "imap.gmail.com");
            properties.setProperty("mail.port", "995");
            properties.setProperty("mail.transport.protocol", "imaps");

            //create store
            session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user,
                                    password);
                        }
                    });
            store = session.getStore("imaps");
            store.connect();

            //create the folder object and open it
            System.out.println(store);
            Folder emailFolder = store.getFolder("corve");
            emailFolder.open(Folder.READ_WRITE);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            Map<String, String> responses = new HashMap<>();

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                message.setFlag(Flags.Flag.SEEN, true);

                String from = message.getFrom()[0].toString();
                if (from.contains("<") && from.contains(">"))
                    from = from.substring(from.indexOf("<") + 1, from.indexOf(">")); //actual email address
                responses.put(message.getSubject(), from);

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

            return responses;

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

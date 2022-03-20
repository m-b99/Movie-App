package ma.ac.ensias.util;

import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.User;

import javax.mail.MessagingException;
import java.security.NoSuchAlgorithmException;

public interface MailSender {

    boolean sendMail(String content, String subject, String email) throws MessagingException;

    boolean sendVerificationEmail(String email, User user) throws MessagingException, ServiceException;

    String calculateMdHash(String str) throws NoSuchAlgorithmException;
}

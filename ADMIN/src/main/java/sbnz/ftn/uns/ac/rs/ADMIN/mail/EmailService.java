package sbnz.ftn.uns.ac.rs.ADMIN.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
        MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mm);

        try {
            mmh.setTo(to);
            mmh.setSubject(subject);
            mmh.setText(body,true);
        } catch (MessagingException e) {
            System.out.println("EXCEPTION MAIL");
            e.printStackTrace();
        }

        javaMailSender.send(mm);
    }

}

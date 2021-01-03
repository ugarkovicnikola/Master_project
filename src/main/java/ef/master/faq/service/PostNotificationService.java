package ef.master.faq.service;

import ef.master.faq.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class PostNotificationService {

  private final JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String senderMail;

  public void sendNotification(Student student) throws MailException {

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo("ugarko.ljkra@gmail.com");
    mailMessage.setFrom(senderMail);
    mailMessage.setSubject("New post has been created!");
    mailMessage.setText("Student - " + student.getFirstName() + " " + student.getLastName() + "created a new post");

    javaMailSender.send(mailMessage);
  }

}

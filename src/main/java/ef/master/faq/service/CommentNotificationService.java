package ef.master.faq.service;

import ef.master.faq.entity.Comment;
import ef.master.faq.entity.Office;
import ef.master.faq.entity.Professor;
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
public class CommentNotificationService {

  private final JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String sender;

  public void sendNotification(Student student, Comment comment) throws MailException {

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(student.getEmail());
    mailMessage.setFrom(sender);
    mailMessage.setSubject("New comment on one of your posts!");
    mailMessage.setText(
        "Dear " + student.getFirstName() + " " + student.getLastName() + System.lineSeparator() + System.lineSeparator()
        + "You have received a comment on your post '" + comment.getPost().getTitle() + "'" + System.lineSeparator()
        + "'" + comment.getText() + "'" + System.lineSeparator() + System.lineSeparator()
        + "Kind regards," + System.lineSeparator()
        + "Master Project Team"
        );

    javaMailSender.send(mailMessage);
  }
}

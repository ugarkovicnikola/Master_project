package ef.master.faq.service;

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
public class AccountNotificationService {

  private final JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String sender;

  public void sendNotificationToStudent(Student student) throws MailException {

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(student.getEmail());
    mailMessage.setFrom(sender);
    mailMessage.setSubject("New account created!");
    mailMessage.setText("You have successfully created new student account with following info: " + System.lineSeparator()
        + "First name: " + student.getFirstName() + System.lineSeparator()
        + " Last name: " + student.getLastName());

    javaMailSender.send(mailMessage);
  }

  public void sendNotificationToProfessor(Professor professor) throws MailException {

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(professor.getEmail());
    mailMessage.setFrom(sender);
    mailMessage.setSubject("New account created!");
    mailMessage.setText("You have successfully created new student account with following info: " + System.lineSeparator()
        + "First name: " + professor.getFirstName() + System.lineSeparator()
        + " Last name: " + professor.getLastName());

    javaMailSender.send(mailMessage);
  }

  public void sendNotificationToOffice(Office office) throws MailException {

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(office.getEmail());
    mailMessage.setFrom(sender);
    mailMessage.setSubject("New account created!");
    mailMessage.setText("You have successfully created new student account with following info: " + System.lineSeparator()
        + "Name: " + office.getName());

    javaMailSender.send(mailMessage);
  }
}

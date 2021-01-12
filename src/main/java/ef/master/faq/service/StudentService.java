package ef.master.faq.service;

import ef.master.faq.dto.StudentRequest;
import ef.master.faq.entity.Student;
import ef.master.faq.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class StudentService {

  private final StudentRepository studentRepository;
  private final MapperFacade mapperFacade;
  private final AccountNotificationService accountNotificationService;

  private final PasswordEncoder passwordEncoder;

  public Student save(@NotNull @Valid StudentRequest studentRequest) {

    Student student = mapperFacade.map(studentRequest, Student.class);

    boolean isEmailAlreadyTaken = studentRepository.existsByEmail(student.getEmail());


    if (isEmailAlreadyTaken) {
      throw new EntityExistsException(String.format("Email %s is already in use", studentRequest.getEmail()));
    }
    student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
    studentRepository.save(student);
    accountNotificationService.sendNotificationToStudent(student);

    return student;
  }

  public List<Student> getAll() {

    return studentRepository.findAll().stream()
        .map(student -> mapperFacade.map(student, Student.class))
        .collect(Collectors.toList());
  }

  public Student getById(@NotNull Long id) {

    return mapperFacade.map(studentRepository.findById(id).orElseThrow(()
        -> new EntityNotFoundException(String.format("Student with ID %s is not found", id))), Student.class);
  }

  public Student updateById(@NotNull @Valid StudentRequest studentRequest, @NotNull Long id) {

    Student student = studentRepository.findById(id).orElseThrow(()
        -> new EntityNotFoundException(String.format("Student with ID %s is not found", id)));
    mapperFacade.map(studentRequest, student);

    student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
    studentRepository.save(student);

    return student;
  }

  public void deleteById(@NotNull Long id) {

    boolean studentExists = studentRepository.existsById(id);

    if (!studentExists) {
      throw new EntityNotFoundException(String.format("Student with ID %s is not found", id));
    }
    studentRepository.deleteById(id);
  }
}
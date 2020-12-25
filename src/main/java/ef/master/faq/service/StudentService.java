package ef.master.faq.service;

import ef.master.faq.dto.StudentRequest;
import ef.master.faq.dto.StudentResponse;
import ef.master.faq.entity.Student;
import ef.master.faq.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
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

  public StudentResponse save(@NotNull @Valid StudentRequest studentRequest) {
    Student student = mapperFacade.map(studentRequest, Student.class);

    boolean isEmailAlreadyTaken = studentRepository.existsByEmail(student.getEmail());

    if (isEmailAlreadyTaken) {
      throw new EntityExistsException(String.format("Email %s is already in use", studentRequest.getEmail()));
    }
    studentRepository.save(student);

    return mapperFacade.map(student, StudentResponse.class);
  }

  public List<StudentResponse> getAll() {
    return studentRepository.findAll().stream()
        .map(student -> mapperFacade.map(student, StudentResponse.class))
        .collect(Collectors.toList());
  }

  public StudentResponse getById(@NotNull Long id) {
    return mapperFacade.map(studentRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Student with ID %s is not found", id))), StudentResponse.class);
  }

  public StudentResponse updateById(@NotNull @Valid StudentRequest studentRequest, @NotNull Long id) {
    Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Student with ID %s is not found", id)));
    student.setFirstName(studentRequest.getFirstName());
    student.setLastName(studentRequest.getLastName());
    student.setEmail(studentRequest.getEmail());
    student.setPassword(studentRequest.getPassword());
    studentRepository.save(student);

    return mapperFacade.map(student, StudentResponse.class);
  }

  public void deleteById(@NotNull Long id) {
    boolean studentExists = studentRepository.existsById(id);

    if (!studentExists) {
      throw new EntityNotFoundException(String.format("Student with ID %s is not found", id));
    }
    studentRepository.deleteById(id);
  }
}
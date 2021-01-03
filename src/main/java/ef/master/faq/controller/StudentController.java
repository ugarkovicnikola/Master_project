package ef.master.faq.controller;

import ef.master.faq.dto.StudentRequest;
import ef.master.faq.dto.StudentResponse;
import ef.master.faq.entity.Student;
import ef.master.faq.service.StudentService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

  private final StudentService studentService;
  private final MapperFacade mapperFacade;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public StudentResponse createStudent (@RequestBody StudentRequest studentRequest) {

    Student student = studentService.save(studentRequest);

    return mapperFacade.map(student, StudentResponse.class);
  }

  @GetMapping("/{id}")
  public StudentResponse getById(@PathVariable Long id) {

    Student student = studentService.getById(id);

    return mapperFacade.map(student, StudentResponse.class);
  }

  @GetMapping
  public List<StudentResponse> getAll() {
    List<Student> studentList = studentService.getAll();

    return mapperFacade.mapAsList(studentList, StudentResponse.class);
  }

  @DeleteMapping("/{id}")
  public void deleteStudent(@PathVariable Long id) {
    studentService.deleteById(id);
  }

  @PutMapping("/{id}")
  public StudentResponse updateStudent(@RequestBody StudentRequest studentRequest, @PathVariable Long id) {

    Student student = studentService.updateById(studentRequest, id);

    return mapperFacade.map(student, StudentResponse.class);
  }
}

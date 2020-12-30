package ef.master.faq.service;

import ef.master.faq.dto.StudentRequest;
import ef.master.faq.entity.Student;
import ef.master.faq.repository.StudentRepository;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private MapperFacade mapperFacade;

  @InjectMocks
  private StudentService studentService;

  @Before
  public void setUp() {
    final MapperFacade originalFacade = new DefaultMapperFactory.Builder().mapNulls(false).build().getMapperFacade();

    given(mapperFacade.map(any(), Mockito.<Class<?>>any())).willAnswer(invocationOnMock -> {
      Object firstArgument = invocationOnMock.getArgument(0);
      Class<?> secondArgument = invocationOnMock.getArgument(1, Class.class);

      return originalFacade.map(firstArgument, secondArgument); });
  }

  @Test
  public void save_ShouldPerformCorrectly() {
    StudentRequest request = new StudentRequest();
    request.setEmail("email@email.com");
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setPassword("password111");

    when(studentRepository.existsByEmail(anyString())).thenReturn(false);

    studentService.save(request);

    verify(studentRepository).save(any(Student.class));
  }

  @Test (expected = EntityExistsException.class)
  public void save_ExistingStudentEmail_ShouldThrowAnException() {
    StudentRequest request = new StudentRequest();
    request.setEmail("email@email.com");
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setPassword("password111");

    when(studentRepository.existsByEmail(anyString())).thenReturn(true);

    studentService.save(request);
  }

  @Test
  public void updateById_ShouldPerformCorrectly() {
    StudentRequest request = new StudentRequest();
    request.setEmail("email@email.com");
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setPassword("password111");

    Student mockStudent = new Student();
    mockStudent.setId(1L);

    when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));

    studentService.updateById(request, 1L);
  }

  @Test(expected = EntityNotFoundException.class)
  public void updateById_NonExistingUserId_ShouldThrowAnException() {
    StudentRequest request = new StudentRequest();
    request.setEmail("email@email.com");
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setPassword("password111");

    when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

    studentService.updateById(request, anyLong());
  }
}
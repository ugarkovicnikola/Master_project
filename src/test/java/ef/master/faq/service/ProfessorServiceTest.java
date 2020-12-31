package ef.master.faq.service;

import ef.master.faq.dto.ProfessorRequest;
import ef.master.faq.entity.Professor;
import ef.master.faq.repository.ProfessorRepository;
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
public class ProfessorServiceTest {

  @Mock
  private ProfessorRepository professorRepository;

  @Mock
  private MapperFacade mapperFacade;

  @InjectMocks
  private ProfessorService professorService;

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
    ProfessorRequest request = new ProfessorRequest();
    request.setEmail("email@email.com");
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setPassword("password111");

    when(professorRepository.existsByEmail(anyString())).thenReturn(false);

    professorService.save(request);

    verify(professorRepository).save(any(Professor.class));
  }

  @Test(expected = EntityExistsException.class)
  public void save_ExistingProfessorEmail_ShouldThrowAnException() {
    ProfessorRequest request = new ProfessorRequest();
    request.setEmail("email@email.com");
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setPassword("password111");

    when(professorRepository.existsByEmail(anyString())).thenReturn(true);

    professorService.save(request);
  }

  @Test
  public void updateById_ShouldPerformCorrectly() {
    ProfessorRequest request = new ProfessorRequest();
    request.setEmail("email@email.com");
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setPassword("password111");

    Professor mockProfessor = new Professor();
    mockProfessor.setId(1L);

    when(professorRepository.findById(1L)).thenReturn(Optional.of(mockProfessor));

    professorService.updateById(request, 1L);
  }

  @Test(expected = EntityNotFoundException.class)
  public void updateById_NonExistingProfessorId_ShouldThrowAnException() {
    ProfessorRequest request = new ProfessorRequest();
    request.setEmail("email@email.com");
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setPassword("password111");

    when(professorRepository.findById(anyLong())).thenReturn(Optional.empty());

    professorService.updateById(request, anyLong());
  }
}
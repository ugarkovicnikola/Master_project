package ef.master.faq.service;

import ef.master.faq.dto.OfficeRequest;
import ef.master.faq.entity.Office;
import ef.master.faq.repository.OfficeRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OfficeServiceTest {

  @Mock
  private OfficeRepository officeRepository;

  @Mock
  private MapperFacade mapperFacade;

  @InjectMocks
  private OfficeService officeService;

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
    OfficeRequest request = new OfficeRequest();
    request.setEmail("email@email.com");
    request.setName("legal");
    request.setPassword("password111");

    when(officeRepository.existsByEmail(anyString())).thenReturn(false);

    officeService.save(request);

    verify(officeRepository).save(any(Office.class));
  }

  @Test(expected = EntityExistsException.class)
  public void save_ExistingOfficeEmail_ShouldThrowAnException() {
    OfficeRequest request = new OfficeRequest();
    request.setEmail("email@email.com");
    request.setName("legal");
    request.setPassword("password111");

    when(officeRepository.existsByEmail(anyString())).thenReturn(true);

    officeService.save(request);
  }

  @Test
  public void updateById_ShouldPerformCorrectly() {
    OfficeRequest request = new OfficeRequest();
    request.setEmail("email@email.com");
    request.setName("legal");
    request.setPassword("password111");

    Office mockOffice = new Office();
    mockOffice.setId(1L);

    when(officeRepository.findById(1L)).thenReturn(Optional.of(mockOffice));

    officeService.updateById(request, 1L);
  }

  @Test(expected = EntityNotFoundException.class)
  public void updateById_NonExistingOfficeId_ShouldThrowAnException() {
    OfficeRequest request = new OfficeRequest();
    request.setEmail("email@email.com");
    request.setName("legal");
    request.setPassword("password111");

    when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());

    officeService.updateById(request, anyLong());
  }
}
package ef.master.faq.service;

import ef.master.faq.dto.TagRequest;
import ef.master.faq.entity.Tag;
import ef.master.faq.repository.TagRepository;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {

  @Mock
  private TagRepository tagRepository;

  @Mock
  private MapperFacade mapperFacade;

  @InjectMocks
  private TagService tagService;

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
    TagRequest request = new TagRequest();
    request.setName("tag01");

    tagService.save(request);

    verify(tagRepository).save(any(Tag.class));
  }

}
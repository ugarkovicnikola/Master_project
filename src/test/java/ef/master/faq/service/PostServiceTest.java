package ef.master.faq.service;

import ef.master.faq.dto.PostRequest;
import ef.master.faq.entity.Post;
import ef.master.faq.entity.Student;
import ef.master.faq.entity.Tag;
import ef.master.faq.exception.IncorrectIdException;
import ef.master.faq.repository.PostRepository;
import ef.master.faq.repository.StudentRepository;
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

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

  @Mock
  private PostRepository postRepository;

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private TagRepository tagRepository;

  @Mock
  private MapperFacade mapperFacade;

  @InjectMocks
  private PostService postService;

  private Student mockStudent;

  @Before
  public void setUp() {
    final MapperFacade originalFacade = new DefaultMapperFactory.Builder().mapNulls(false).build().getMapperFacade();

    given(mapperFacade.map(any(), Mockito.<Class<?>>any())).willAnswer(invocationOnMock -> {
      Object firstArgument = invocationOnMock.getArgument(0);
      Class<?> secondArgument = invocationOnMock.getArgument(1, Class.class);

      return originalFacade.map(firstArgument, secondArgument); });

    mockStudent = new Student();
    mockStudent.setId(1L);
  }

  @Test
  public void save_ShouldPerformCorrectly() {
    PostRequest request = new PostRequest();
    request.setTitle("Post 1");
    request.setContent("Just some text");
    request.setStudentId(1L);
    request.setTagIds(Stream.of(1L, 2L, 3L).collect(Collectors.toSet()));

    when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mockStudent));
    when(tagRepository.findAllByIdIn(anyCollection())).thenAnswer(invocationOnMock -> {
      int providedTagsIdsSize = invocationOnMock.getArgument(0, Set.class).size();

      return LongStream.range(0, providedTagsIdsSize).mapToObj(id -> {
        Tag tag = new Tag();
        tag.setId(id);

        return tag;
      }).collect(Collectors.toList());
    });

    postService.save(request);

    verify(postRepository).save(any(Post.class));
  }

  @Test(expected = EntityNotFoundException.class)
  public void save_NonExistingUserId_ShouldThrowAnException() {
    PostRequest request = new PostRequest();
    request.setTitle("Post 1");
    request.setContent("Just some text");

    when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

    postService.save(request);
  }

  @Test(expected = IncorrectIdException.class)
  public void save_IncorrectTagId_ShouldThrowAnException() {
    PostRequest request = new PostRequest();
    request.setTitle("Post 1");
    request.setContent("Just some text");
    request.setStudentId(1L);
    request.setTagIds(Stream.of(1L, 2L, 3L).collect(Collectors.toSet()));

    when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mockStudent));
    when(tagRepository.findAllByIdIn(anyCollection())).thenAnswer(invocationOnMock -> {
      int providedTagsIdsSize = invocationOnMock.getArgument(0, Set.class).size();

      return LongStream.range(0, providedTagsIdsSize - 1).mapToObj(id -> {
        Tag tag = new Tag();
        tag.setId(id);

        return tag;
      }).collect(Collectors.toList());
    });

    postService.save(request);
  }

  @Test
  public void updateById_ShouldPerformCorrectly() {
    Post mockPost = new Post();
    mockPost.setStudent(mockStudent);

    PostRequest request = new PostRequest();
    request.setTitle("Post 1");
    request.setContent("Just some text");
    request.setStudentId(1L);

    when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));

    postService.updateById(request, 1L);
  }

  @Test(expected = EntityNotFoundException.class)
  public void updateById_NonExistingPostId_ShouldThrowAnException() {
    PostRequest request = new PostRequest();
    request.setTitle("Post 1");
    request.setContent("Just some text");

    when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

    postService.updateById(request, anyLong());
  }

  @Test(expected = IncorrectIdException.class)
  public void updateById_IncorrectStudentId_ShouldThrowAnException() {
    Post mockPost = new Post();
    mockPost.setStudent(mockStudent);

    PostRequest request = new PostRequest();
    request.setTitle("Post 1");
    request.setContent("Just some text");
    request.setStudentId(2L);

    when(postRepository.findById(anyLong())).thenReturn(Optional.of(mockPost));

    postService.updateById(request, 1L);
  }

}
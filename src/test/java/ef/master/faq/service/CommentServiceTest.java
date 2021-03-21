package ef.master.faq.service;

import ef.master.faq.dto.CommentRequest;
import ef.master.faq.entity.Office;
import ef.master.faq.entity.Comment;
import ef.master.faq.entity.Post;
import ef.master.faq.entity.Professor;
import ef.master.faq.entity.Student;
import ef.master.faq.exception.IncorrectIdException;
import ef.master.faq.repository.OfficeRepository;
import ef.master.faq.repository.CommentRepository;
import ef.master.faq.repository.PostRepository;
import ef.master.faq.repository.ProfessorRepository;
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

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

  @Mock
  private CommentRepository commentRepository;

  @Mock
  private PostRepository postRepository;

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private ProfessorRepository professorRepository;

  @Mock
  private OfficeRepository officeRepository;

  @Mock
  private MapperFacade mapperFacade;

  @InjectMocks
  private CommentService commentService;

  private Student mockStudent;
  private Professor mockProfessor;
  private Office mockOffice;
  private Post mockPost;

  @Before
  public void setUp() {
    final MapperFacade originalFacade = new DefaultMapperFactory.Builder().mapNulls(false).build().getMapperFacade();

    given(mapperFacade.map(any(), Mockito.<Class<?>>any())).willAnswer(invocationOnMock -> {
      Object firstArgument = invocationOnMock.getArgument(0);
      Class<?> secondArgument = invocationOnMock.getArgument(1, Class.class);

      return originalFacade.map(firstArgument, secondArgument); });

    mockStudent = new Student();
    mockStudent.setId(1L);

    mockProfessor = new Professor();
    mockProfessor.setId(1L);

    mockOffice = new Office();
    mockOffice.setId(1L);

    mockPost = new Post();
    mockPost.setId(1L);
  }

  @Test(expected = EntityNotFoundException.class)
  public void createComment_NonExistingStudentId_ShouldThrowAnException() {
    CommentRequest request = new CommentRequest();
    request.setText("comment 1");
    request.setStudentId(5L);

    when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

    commentService.save(request);
  }

  @Test(expected = EntityNotFoundException.class)
  public void createComment_NonExistingProfessorId_ShouldThrowAnException() {
    CommentRequest request = new CommentRequest();
    request.setText("comment 1");
    request.setProfessorId(5L);

    when(professorRepository.findById(anyLong())).thenReturn(Optional.empty());

    commentService.save(request);
  }

  @Test(expected = EntityNotFoundException.class)
  public void createComment_NonExistingOfficeId_ShouldThrowAnException() {
    CommentRequest request = new CommentRequest();
    request.setText("comment 1");
    request.setOfficeId(5L);

    when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());

    commentService.save(request);
  }

  @Test(expected = EntityNotFoundException.class)
  public void createComment_NonExistingPostId_ShouldThrowAnException() {
    CommentRequest request = new CommentRequest();
    request.setText("comment 1");
    request.setStudentId(mockStudent.getId());

    when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

    commentService.save(request);
  }

  @Test(expected = EntityNotFoundException.class)
  public void updateById_NonExistingCommentId_ShouldThrowAnException() {
    CommentRequest request = new CommentRequest();
    request.setText("comment 1");

    when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

    commentService.updateById(request, anyLong());
  }

  @Test(expected = IncorrectIdException.class)
  public void updateById_IncorrectStudentId_ShouldThrowAnException() {
    Comment mockComment = new Comment();
    mockComment.setStudent(mockStudent);

    CommentRequest request = new CommentRequest();
    request.setText("comment 1");
    request.setStudentId(5L);

    when(commentRepository.findById(anyLong())).thenReturn(Optional.of(mockComment));

    commentService.updateById(request, anyLong());
  }

  @Test(expected = IncorrectIdException.class)
  public void updateById_IncorrectProfessorId_ShouldThrowAnException() {
    Comment mockComment = new Comment();
    mockComment.setProfessor(mockProfessor);

    CommentRequest request = new CommentRequest();
    request.setText("comment 1");
    request.setProfessorId(5L);

    when(commentRepository.findById(anyLong())).thenReturn(Optional.of(mockComment));

    commentService.updateById(request, anyLong());
  }

  @Test(expected = IncorrectIdException.class)
  public void updateById_IncorrectOfficeId_ShouldThrowAnException() {
    Comment mockComment = new Comment();
    mockComment.setOffice(mockOffice);

    CommentRequest request = new CommentRequest();
    request.setText("comment 1");
    request.setOfficeId(5L);

    when(commentRepository.findById(anyLong())).thenReturn(Optional.of(mockComment));

    commentService.updateById(request, anyLong());
  }

  @Test(expected = IncorrectIdException.class)
  public void updateById_IncorrectPostId_ShouldThrowAnException() {
    Comment mockComment = new Comment();
    mockComment.setStudent(mockStudent);
    mockComment.setPost(mockPost);

    CommentRequest request = new CommentRequest();
    request.setText("comment 1");
    request.setStudentId(1L);
    request.setPostId(5L);

    when(commentRepository.findById(anyLong())).thenReturn(Optional.of(mockComment));

    commentService.updateById(request, anyLong());
  }
}
package ef.master.faq.service;

import ef.master.faq.dto.CommentRequest;
import ef.master.faq.entity.Comment;
import ef.master.faq.entity.Office;
import ef.master.faq.entity.Post;
import ef.master.faq.entity.Professor;
import ef.master.faq.entity.Student;
import ef.master.faq.exception.IncorrectIdException;
import ef.master.faq.repository.CommentRepository;
import ef.master.faq.repository.OfficeRepository;
import ef.master.faq.repository.PostRepository;
import ef.master.faq.repository.ProfessorRepository;
import ef.master.faq.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class CommentService {

  private final CommentRepository commentRepository;
  private final StudentRepository studentRepository;
  private final ProfessorRepository professorRepository;
  private final OfficeRepository officeRepository;
  private final PostRepository postRepository;
  private final CommentNotificationService commentNotificationService;
  private final MapperFacade mapperFacade;

  public Comment save(@NotNull @Valid CommentRequest commentRequest) {

    Comment comment = mapperFacade.map(commentRequest, Comment.class);

    if (commentRequest.getStudentId() > 0) {
      Student student = studentRepository.findById(commentRequest.getStudentId()).orElseThrow(()
          -> new EntityNotFoundException(String.format("Student with ID %s is not found", commentRequest.getStudentId())));
      comment.setStudent(student);
    }

    if (commentRequest.getProfessorId() > 0) {
      Professor professor = professorRepository.findById(commentRequest.getProfessorId()).orElseThrow(()
          -> new EntityNotFoundException(String.format("Professor with ID %s is not found", commentRequest.getProfessorId())));
      comment.setProfessor(professor);
    }

    if (commentRequest.getOfficeId() > 0) {
      Office office = officeRepository.findById(commentRequest.getOfficeId()).orElseThrow(()
          -> new EntityNotFoundException(String.format("Office with ID %s is not found", commentRequest.getOfficeId())));
      comment.setOffice(office);
    }

    Post post = postRepository.findById(commentRequest.getPostId()).orElseThrow(()
        -> new EntityNotFoundException(String.format("Post with ID %s is not found", commentRequest.getPostId())));

    comment.setPost(post);

    commentRepository.save(comment);
    commentNotificationService.sendNotification(post.getStudent(), comment);

    return mapperFacade.map(comment, Comment.class);
  }

  public List<Comment> getAll() {

    return commentRepository.findAll().stream()
        .map(comment -> mapperFacade.map(comment, Comment.class))
        .collect(Collectors.toList());
  }

  public Comment getById(@NotNull Long id) {

    return mapperFacade.map(commentRepository.findById(id).orElseThrow(()
        -> new EntityNotFoundException(String.format("Comment with ID %s is not found",id))), Comment.class);
  }

  public Comment updateById(@NotNull @Valid CommentRequest commentRequest, @NotNull Long id) {

    Comment comment = commentRepository.findById(id).orElseThrow(()
        -> new EntityNotFoundException(String.format("Comment with ID %s is not found", id)));
    comment.setText(commentRequest.getText());

    if (comment.getStudent() != null) {
      boolean studentIdMatching = comment.getStudent().getId().equals(commentRequest.getStudentId());

      if (!studentIdMatching) {
        throw new IncorrectIdException("Student id's are not matching");
      }
    }

    if (comment.getProfessor() != null) {
      boolean professorIdMatching = comment.getProfessor().getId().equals(commentRequest.getProfessorId());

      if (!professorIdMatching) {
        throw new IncorrectIdException("Professor id's are not matching");
      }
    }

    if (comment.getOffice() != null) {
      boolean officeIdMatching = comment.getOffice().getId().equals(commentRequest.getOfficeId());

      if (!officeIdMatching) {
        throw new IncorrectIdException("Office id's are not matching");
      }
    }

    boolean postIdMatching = comment.getPost().getId().equals(commentRequest.getPostId());

    if (!postIdMatching) {
      throw new IncorrectIdException("Post id's are not matching");
    }

    commentRepository.save(comment);

    return mapperFacade.map(comment, Comment.class);
  }

  public void deleteById(@NotNull Long id) {

    boolean exists = commentRepository.existsById(id);

    if (!exists) {
      throw new EntityNotFoundException(String.format("Comment with ID %s is not found", id));
    }
    commentRepository.deleteById(id);
  }

  public void upVote(@NotNull Long id) {

    Comment comment = this.getById(id);
    comment.upVote();
    commentRepository.save(comment);
  }

  public void downVote(@NotNull Long id) {

    Comment comment = this.getById(id);
    comment.downVote();
    commentRepository.save(comment);
  }
}

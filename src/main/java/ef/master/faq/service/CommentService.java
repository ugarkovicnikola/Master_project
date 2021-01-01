package ef.master.faq.service;

import ef.master.faq.dto.CommentRequest;
import ef.master.faq.dto.CommentResponse;
import ef.master.faq.entity.Comment;
import ef.master.faq.entity.Post;
import ef.master.faq.entity.Student;
import ef.master.faq.exception.IncorrectIdException;
import ef.master.faq.repository.CommentRepository;
import ef.master.faq.repository.PostRepository;
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
  private final PostRepository postRepository;
  private final MapperFacade mapperFacade;

  public CommentResponse save(@NotNull @Valid CommentRequest commentRequest) {
    Comment comment = mapperFacade.map(commentRequest, Comment.class);
    Student student = studentRepository.findById(commentRequest.getStudentId()).orElseThrow(() -> new EntityNotFoundException(String.format("Student with ID %s is not found", commentRequest.getStudentId())));
    Post post = postRepository.findById(commentRequest.getPostId()).orElseThrow(() -> new EntityNotFoundException(String.format("Post with ID %s is not found", commentRequest.getPostId())));
    comment.setStudent(student);
    comment.setPost(post);

    commentRepository.save(comment);

    return mapperFacade.map(comment, CommentResponse.class);
  }

  public List<CommentResponse> getAll() {
    return commentRepository.findAll().stream()
        .map(comment -> mapperFacade.map(comment, CommentResponse.class))
        .collect(Collectors.toList());
  }

  public CommentResponse getById(@NotNull Long id) {
    return mapperFacade.map(commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Comment with ID %s is not found",id))), CommentResponse.class);
  }

  public CommentResponse updateById(@NotNull @Valid CommentRequest commentRequest, @NotNull Long id) {
    Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Comment with ID %s is not found", id)));
    comment.setText(commentRequest.getText());

    boolean studentIdMatching = comment.getStudent().getId().equals(commentRequest.getStudentId());

    if (!studentIdMatching) {
      throw new IncorrectIdException("User id's are not matching");
    }

    boolean postIdMatching = comment.getPost().getId().equals(commentRequest.getPostId());

    if (!postIdMatching) {
      throw new IncorrectIdException("Post id's are not matching");
    }

    commentRepository.save(comment);

    return mapperFacade.map(comment, CommentResponse.class);
  }

  public void deleteById(@NotNull Long id) {
    boolean exists = commentRepository.existsById(id);

    if (!exists) {
      throw new EntityNotFoundException(String.format("Comment with ID %s is not found", id));
    }
    commentRepository.deleteById(id);
  }

  public void commentUpvote(@NotNull Long id) {
    Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Comment with ID %s is not found", id)));
    comment.setNumberOfUpVotes(comment.getNumberOfUpVotes()+1L);
  }

  public void commentDownvote(@NotNull Long id) {
    Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Comment with ID %s is not found", id)));
    comment.setNumberOfDownVotes(comment.getNumberOfDownVotes()+1L);
  }
}

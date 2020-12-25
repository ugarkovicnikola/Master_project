package ef.master.faq.service;

import ef.master.faq.dto.PageResponse;
import ef.master.faq.dto.PostRequest;
import ef.master.faq.dto.PostResponse;
import ef.master.faq.entity.Post;
import ef.master.faq.entity.Student;
import ef.master.faq.entity.Tag;
import ef.master.faq.exception.IncorrectIdException;
import ef.master.faq.repository.PostRepository;
import ef.master.faq.repository.StudentRepository;
import ef.master.faq.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class PostService {

  private final PostRepository postRepository;
  private final StudentRepository studentRepository;
  private final TagRepository tagRepository;
  private final MapperFacade mapperFacade;
  private static final long JPA_PAGINATION_INDEX_DIFFERENCE  = 1;

  public PostResponse save(@NotNull @Valid PostRequest postRequest) {
    Post post = mapperFacade.map(postRequest, Post.class);
    Student student = studentRepository.findById(postRequest.getStudentId()).orElseThrow(() -> new EntityNotFoundException(String.format("Student with ID %s is not found", postRequest.getStudentId())));

    List<Tag> tags = tagRepository.findAllByIdIn(postRequest.getTagIds());

    if (tags.size() != postRequest.getTagIds().size()) {
      throw new IncorrectIdException("You have entered non existing Tag ids");
    }

    post.setStudent(student);
    post.setTags(tags);
    postRepository.save(post);

    return mapperFacade.map(post, PostResponse.class);
  }

  public PageResponse<PostResponse> getAllPosts(@NotNull Integer pageNumber, @NotNull Integer pageSize, @NotNull String sortBy) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

    Page<Post> pagedResult = postRepository.findAll(pageable);

    PageResponse<PostResponse> pageResponse = new PageResponse<>();
    pageResponse.setTotalItems(pagedResult.getTotalElements());
    pageResponse.setPageNumber(pageNumber.longValue() + JPA_PAGINATION_INDEX_DIFFERENCE);
    pageResponse.setPageSize(pageSize.longValue());

    pageResponse.setItems(pagedResult.getContent().stream()
    .map(post -> mapperFacade.map(post, PostResponse.class))
    .collect(Collectors.toList()));

    return pageResponse;
  }

  public PostResponse getById(@NotNull Long id) {
    return mapperFacade.map(postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Post with ID %s is not found", id))), PostResponse.class);
  }

  public PostResponse updateById(@NotNull @Valid PostRequest postRequest, @NotNull Long id) {
    Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Post with ID %s is not found", id)));
    Student student = studentRepository.findById(postRequest.getStudentId()).orElseThrow(() -> new EntityNotFoundException(String.format("Student with ID %s is not found", postRequest.getStudentId())));

    List<Tag> tags = tagRepository.findAllByIdIn(postRequest.getTagIds());

    if (tags.size() != postRequest.getTagIds().size()) {
      throw new IncorrectIdException("You have entered non existing Tag ids");
    }

    post.setTitle(postRequest.getTitle());
    post.setContent(postRequest.getContent());
    post.setStudent(student);
    post.setTags(tags);

    postRepository.save(post);

    return mapperFacade.map(post, PostResponse.class);
  }

  public void deleteById(@NotNull Long id) {
    boolean postExists = postRepository.existsById(id);

    if (!postExists) {
      throw new EntityNotFoundException(String.format("Post with ID %s is not found", id));
    }
    postRepository.deleteById(id);
  }
}
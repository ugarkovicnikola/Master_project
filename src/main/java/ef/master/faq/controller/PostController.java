package ef.master.faq.controller;

import ef.master.faq.dto.PageResponse;
import ef.master.faq.dto.PostRequest;
import ef.master.faq.dto.PostResponse;
import ef.master.faq.entity.Post;
import ef.master.faq.service.PostService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

  private final PostService postService;
  private final MapperFacade mapperFacade;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PostResponse createPost(@RequestBody PostRequest postRequest) {
    Post post = postService.save(postRequest);
    return mapperFacade.map(post, PostResponse.class);
  }

  @GetMapping("/{id}")
  public PostResponse getById(@PathVariable Long id) {
    Post post = postService.getById(id);
    PostResponse postResponse = mapperFacade.map(post, PostResponse.class);
    postResponse.setNumberOfComments(post.getComments().size());
    return postResponse;
  }

  @GetMapping
  public PageResponse<PostResponse> getAll(@RequestParam Integer pageNumber,
                                           @RequestParam Integer pageSize,
                                           @RequestParam String sortBy) {

    return postService.getAllPosts(pageNumber,pageSize,sortBy);
  }

  @GetMapping("/student/{id}")
  public List<PostResponse> getByStudentId(@PathVariable Long id) {
    List<Post> postList = postService.getByStudentId(id);
    return mapperFacade.mapAsList(postList, PostResponse.class);
  }

  @DeleteMapping("/{id}")
  public void deletePost(@PathVariable Long id) {
    postService.deleteById(id);
  }

  @PutMapping("/{id}")
  public PostResponse updateById(@RequestBody PostRequest postRequest, @PathVariable Long id) {
    Post post = postService.updateById(postRequest, id);
    PostResponse postResponse = mapperFacade.map(post, PostResponse.class);
    postResponse.setNumberOfComments(post.getComments().size());
    return postResponse;
  }
}

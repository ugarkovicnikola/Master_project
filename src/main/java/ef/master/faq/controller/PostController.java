package ef.master.faq.controller;

import ef.master.faq.dto.PageResponse;
import ef.master.faq.dto.PostRequest;
import ef.master.faq.dto.PostResponse;
import ef.master.faq.service.PostService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

  private final PostService postService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PostResponse createPost(@RequestBody PostRequest postRequest) {
    return postService.save(postRequest);
  }

  @GetMapping("/{id}")
  public PostResponse getById(@PathVariable Long id) {
    return postService.getById(id);
  }

  @GetMapping
  public PageResponse<PostResponse> getAll(@RequestParam Integer pageNumber,
                                           @RequestParam Integer pageSize,
                                           @RequestParam String sortBy) {

    return postService.getAllPosts(pageNumber,pageSize,sortBy);
  }

  @DeleteMapping("/{id}")
  public void deletePost(@PathVariable Long id) {
    postService.deleteById(id);
  }

  @PutMapping("/{id}")
  public PostResponse updateById(@RequestBody PostRequest postRequest, @PathVariable Long id) {
    return postService.updateById(postRequest, id);
  }
}

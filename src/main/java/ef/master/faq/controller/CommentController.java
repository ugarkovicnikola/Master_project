package ef.master.faq.controller;

import ef.master.faq.dto.CommentRequest;
import ef.master.faq.dto.CommentResponse;
import ef.master.faq.dto.PostRequest;
import ef.master.faq.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CommentResponse createComment(@RequestBody CommentRequest commentRequest) {
    return commentService.save(commentRequest);
  }

  @GetMapping("/id")
  public CommentResponse getById(@PathVariable Long id) {
    return commentService.getById(id);
  }

  @GetMapping
  public List<CommentResponse> getAll() {
    return commentService.getAll();
  }

  @DeleteMapping("/{id}")
  public void deleteComment(@PathVariable Long id) {
    commentService.deleteById(id);
  }

  @PutMapping
  public CommentResponse updateById(@RequestBody CommentRequest commentRequest, @PathVariable Long id) {
    return commentService.updateById(commentRequest,id);
  }
}
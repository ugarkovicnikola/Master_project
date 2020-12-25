package ef.master.faq.controller;

import ef.master.faq.dto.TagRequest;
import ef.master.faq.dto.TagResponse;
import ef.master.faq.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {

  private final TagService tagService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TagResponse createTag(@RequestBody TagRequest tagRequest) {
    return tagService.save(tagRequest);
  }

  @GetMapping
  public List<TagResponse> getAll() {
    return tagService.getAll();
  }
}

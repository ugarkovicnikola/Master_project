package ef.master.faq.controller;

import ef.master.faq.dto.TagRequest;
import ef.master.faq.dto.TagResponse;
import ef.master.faq.entity.Tag;
import ef.master.faq.service.TagService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final MapperFacade mapperFacade;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TagResponse createTag(@RequestBody TagRequest tagRequest) {
    Tag tag = tagService.save(tagRequest);

    return mapperFacade.map(tag, TagResponse.class);
  }

  @GetMapping
  public List<TagResponse> getAll() {
    List<Tag> tagList = tagService.getAll();

    return mapperFacade.mapAsList(tagList, TagResponse.class);
  }

  @DeleteMapping("{id}")
  public void deleteTag(@PathVariable Long id) {
    tagService.deleteById(id);
  }
}

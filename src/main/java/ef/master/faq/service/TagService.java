package ef.master.faq.service;

import ef.master.faq.dto.TagRequest;
import ef.master.faq.dto.TagResponse;
import ef.master.faq.entity.Tag;
import ef.master.faq.repository.TagRepository;
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
public class TagService {

  private final TagRepository tagRepository;
  private final MapperFacade mapperFacade;

  public TagResponse save(@NotNull @Valid TagRequest tagRequest) {
    Tag tag = mapperFacade.map(tagRequest, Tag.class);

    tagRepository.save(tag);

    return mapperFacade.map(tag, TagResponse.class);
  }

  public List<TagResponse> getAll() {
    return tagRepository.findAll().stream()
        .map(tag -> mapperFacade.map(tag, TagResponse.class))
        .collect(Collectors.toList());
  }

  public void deleteById(@NotNull Long id) {
    boolean tagExists = tagRepository.existsById(id);

    if (!tagExists) {
      throw new EntityNotFoundException(String.format("Tag with ID %s is not found", id));
    }
    tagRepository.deleteById(id);
  }
}
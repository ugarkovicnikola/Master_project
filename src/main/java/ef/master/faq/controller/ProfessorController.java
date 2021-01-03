package ef.master.faq.controller;

import ef.master.faq.dto.ProfessorRequest;
import ef.master.faq.dto.ProfessorResponse;
import ef.master.faq.entity.Professor;
import ef.master.faq.service.ProfessorService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/professor")
public class ProfessorController {

  private final ProfessorService professorService;
  private final MapperFacade mapperFacade;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProfessorResponse createProfessor (@RequestBody ProfessorRequest professorRequest) {

    Professor professor = professorService.save(professorRequest);

    return mapperFacade.map(professor, ProfessorResponse.class);
  }

  @GetMapping("/{id}")
  public ProfessorResponse getById(@PathVariable Long id) {
    Professor professor = professorService.getById(id);

    return mapperFacade.map(professor, ProfessorResponse.class);
  }

  @GetMapping
  public List<ProfessorResponse> getAll() {
    List<Professor> professorList = professorService.getAll();

    return mapperFacade.mapAsList(professorList, ProfessorResponse.class);
  }

  @DeleteMapping("/{id}")
  public void deleteProfessor(@PathVariable Long id) {
    professorService.deleteById(id);
  }

  @PutMapping("/{id}")
  public ProfessorResponse updateProfessor(@RequestBody ProfessorRequest professorRequest, @PathVariable Long id) {
    Professor professor = professorService.updateById(professorRequest, id);

    return mapperFacade.map(professor, ProfessorResponse.class);
  }
}

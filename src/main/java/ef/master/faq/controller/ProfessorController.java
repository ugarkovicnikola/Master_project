package ef.master.faq.controller;

import ef.master.faq.dto.ProfessorRequest;
import ef.master.faq.dto.ProfessorResponse;
import ef.master.faq.service.ProfessorService;
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
@RequestMapping("/professor")
public class ProfessorController {

  private final ProfessorService professorService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProfessorResponse createProfessor (@RequestBody ProfessorRequest professorRequest) {
    return professorService.save(professorRequest);
  }

  @GetMapping("/{id}")
  public ProfessorResponse getById(@PathVariable Long id) {
    return professorService.getById(id);
  }

  @GetMapping
  public List<ProfessorResponse> getAll() {
    return professorService.getAll();
  }

  @DeleteMapping("/{id}")
  public void deleteProfessor(@PathVariable Long id) {
    professorService.deleteById(id);
  }

  @PutMapping("/{id}")
  public ProfessorResponse updateProfessor(@RequestBody ProfessorRequest professorRequest, @PathVariable Long id) {
    return professorService.updateById(professorRequest, id);
  }
}

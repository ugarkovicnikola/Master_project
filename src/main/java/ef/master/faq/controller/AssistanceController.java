package ef.master.faq.controller;

import ef.master.faq.dto.AssistanceRequest;
import ef.master.faq.dto.AssistanceResponse;
import ef.master.faq.dto.StudentRequest;
import ef.master.faq.dto.StudentResponse;
import ef.master.faq.service.AssistanceService;
import ef.master.faq.service.StudentService;
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
@RequestMapping("/assistance")
public class AssistanceController {

  private final AssistanceService assistanceService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AssistanceResponse createAssistance (@RequestBody AssistanceRequest assistanceRequest) {
    return assistanceService.save(assistanceRequest);
  }

  @GetMapping("/{id}")
  public AssistanceResponse getById(@PathVariable Long id) {
    return assistanceService.getById(id);
  }

  @GetMapping
  public List<AssistanceResponse> getAll() {
    return assistanceService.getAll();
  }

  @DeleteMapping("/{id}")
  public void deleteAssistance(@PathVariable Long id) {
    assistanceService.deleteById(id);
  }

  @PutMapping("/{id}")
  public AssistanceResponse updateAssistance(@RequestBody AssistanceRequest assistanceRequest, @PathVariable Long id) {
    return assistanceService.updateById(assistanceRequest, id);
  }
}

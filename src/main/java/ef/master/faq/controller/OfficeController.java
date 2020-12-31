package ef.master.faq.controller;

import ef.master.faq.dto.OfficeRequest;
import ef.master.faq.dto.OfficeResponse;
import ef.master.faq.service.OfficeService;
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
@RequestMapping("/office")
public class OfficeController {

  private final OfficeService officeService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OfficeResponse createAssistance (@RequestBody OfficeRequest officeRequest) {
    return officeService.save(officeRequest);
  }

  @GetMapping("/{id}")
  public OfficeResponse getById(@PathVariable Long id) {
    return officeService.getById(id);
  }

  @GetMapping
  public List<OfficeResponse> getAll() {
    return officeService.getAll();
  }

  @DeleteMapping("/{id}")
  public void deleteAssistance(@PathVariable Long id) {
    officeService.deleteById(id);
  }

  @PutMapping("/{id}")
  public OfficeResponse updateAssistance(@RequestBody OfficeRequest officeRequest, @PathVariable Long id) {
    return officeService.updateById(officeRequest, id);
  }
}

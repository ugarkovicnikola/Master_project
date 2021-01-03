package ef.master.faq.controller;

import ef.master.faq.dto.OfficeRequest;
import ef.master.faq.dto.OfficeResponse;
import ef.master.faq.entity.Office;
import ef.master.faq.service.OfficeService;
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
@RequestMapping("/office")
public class OfficeController {

  private final OfficeService officeService;
  private final MapperFacade mapperFacade;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OfficeResponse createOffice (@RequestBody OfficeRequest officeRequest) {
    Office office = officeService.save(officeRequest);

    return mapperFacade.map(office, OfficeResponse.class);
  }

  @GetMapping("/{id}")
  public OfficeResponse getById(@PathVariable Long id) {
    Office office = officeService.getById(id);

    return mapperFacade.map(office, OfficeResponse.class);
  }

  @GetMapping
  public List<OfficeResponse> getAll() {
    List<Office> officeList = officeService.getAll();

    return mapperFacade.mapAsList(officeList, OfficeResponse.class);
  }

  @DeleteMapping("/{id}")
  public void deleteOffice(@PathVariable Long id) {
    officeService.deleteById(id);
  }

  @PutMapping("/{id}")
  public OfficeResponse updateOffice(@RequestBody OfficeRequest officeRequest, @PathVariable Long id) {
    Office office = officeService.updateById(officeRequest, id);

    return mapperFacade.map(office, OfficeResponse.class);
  }
}

package ef.master.faq.service;

import ef.master.faq.dto.OfficeRequest;
import ef.master.faq.entity.Office;
import ef.master.faq.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class OfficeService {

  private final OfficeRepository officeRepository;
  private final MapperFacade mapperFacade;
  private final AccountNotificationService accountNotificationService;
  private final PasswordEncoder passwordEncoder;

  public Office save(@NotNull @Valid OfficeRequest officeRequest) {

    Office office = mapperFacade.map(officeRequest, Office.class);

    boolean isEmailAlreadyTaken = officeRepository.existsByEmail(office.getEmail());

    if (isEmailAlreadyTaken) {
      throw new EntityExistsException(String.format("Email %s is already in use", officeRequest.getEmail()));
    }
    office.setPassword(passwordEncoder.encode(officeRequest.getPassword()));
    officeRepository.save(office);
    accountNotificationService.sendNotificationToOffice(office);

    return office;
  }

  public List<Office> getAll() {

    return officeRepository.findAll().stream()
        .map(office -> mapperFacade.map(office, Office.class))
        .collect(Collectors.toList());
  }
  
  public Office getById(@NotNull Long id) {

    return mapperFacade.map(officeRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format
            ("Service with ID %s is not found", id))), Office.class);
  }

  public Office updateById(@NotNull @Valid OfficeRequest officeRequest, @NotNull Long id) {

    Office office = officeRepository.findById(id).orElseThrow(()
        -> new EntityNotFoundException(String.format("Service with ID %s is not found", id)));
    mapperFacade.map(officeRequest, office);

    office.setPassword(passwordEncoder.encode(officeRequest.getPassword()));
    officeRepository.save(office);
    
    return mapperFacade.map(office, Office.class);
  }
  
  public void deleteById(@NotNull Long id) {

    boolean professorExists = officeRepository.existsById(id);
    
    if (!professorExists) {
      throw new EntityNotFoundException(String.format("Professor with ID %s is not found", id));
    }
    officeRepository.deleteById(id);
  }
}
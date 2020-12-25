package ef.master.faq.service;

import ef.master.faq.dto.AssistanceRequest;
import ef.master.faq.dto.AssistanceResponse;
import ef.master.faq.entity.Assistance;
import ef.master.faq.repository.AssistanceRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
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
public class AssistanceService {

  private final AssistanceRepository assistanceRepository;
  private final MapperFacade mapperFacade;

  public AssistanceResponse save(@NotNull @Valid AssistanceRequest assistanceRequest) {
    Assistance assistance = mapperFacade.map(assistanceRequest, Assistance.class);

    boolean isEmailAlreadyTaken = assistanceRepository.existsByEmail(assistance.getEmail());

    if (isEmailAlreadyTaken) {
      throw new EntityExistsException(String.format("Email %s is already in use", assistanceRequest.getEmail()));
    }
    assistanceRepository.save(assistance);

    return mapperFacade.map(assistance, AssistanceResponse.class);
  }

  public List<AssistanceResponse> getAll() {
    return assistanceRepository.findAll().stream()
        .map(assistance -> mapperFacade.map(assistance, AssistanceResponse.class))
        .collect(Collectors.toList());
  }
  
  public AssistanceResponse getById(@NotNull Long id) {
    return mapperFacade.map(assistanceRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Service with ID %s is not found", id))), AssistanceResponse.class);
  }

  public AssistanceResponse updateById(@NotNull @Valid AssistanceRequest assistanceRequest, @NotNull Long id) {
    Assistance assistance = assistanceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Service with ID %s is not found", id)));
    assistance.setNameOfService(assistanceRequest.getNameOfService());
    assistance.setEmail(assistanceRequest.getEmail());
    assistance.setPassword(assistanceRequest.getPassword());
    assistanceRepository.save(assistance);
    
    return mapperFacade.map(assistance, AssistanceResponse.class);
  }
  
  public void deleteById(@NotNull Long id) {
    boolean professorExists = assistanceRepository.existsById(id);
    
    if (!professorExists) {
      throw new EntityNotFoundException(String.format("Professor with ID %s is not found", id));
    }
    assistanceRepository.deleteById(id);
  }
}
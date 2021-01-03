package ef.master.faq.service;

import ef.master.faq.dto.ProfessorRequest;
import ef.master.faq.entity.Professor;
import ef.master.faq.repository.ProfessorRepository;
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
public class ProfessorService {

  private final ProfessorRepository professorRepository;
  private final MapperFacade mapperFacade;
  private final PasswordEncoder passwordEncoder;

  public Professor save(@NotNull @Valid ProfessorRequest professorRequest) {

    Professor professor = mapperFacade.map(professorRequest, Professor.class);

    boolean isEmailAlreadyTaken = professorRepository.existsByEmail(professor.getEmail());

    if (isEmailAlreadyTaken) {
      throw new EntityExistsException(String.format("Email %s is already in use", professorRequest.getEmail()));
    }
    professor.setPassword(passwordEncoder.encode(professorRequest.getPassword()));
    professorRepository.save(professor);

    return professor;
  }

  public List<Professor> getAll() {

    return professorRepository.findAll().stream()
        .map(professor -> mapperFacade.map(professor, Professor.class))
        .collect(Collectors.toList());
  }
  
  public Professor getById(@NotNull Long id) {

    return mapperFacade.map(professorRepository.findById(id).orElseThrow(()
        -> new EntityNotFoundException(String.format("Professor with ID %s is not found", id))), Professor.class);
  }

  public Professor updateById(@NotNull @Valid ProfessorRequest professorRequest, @NotNull Long id) {

    Professor professor = professorRepository.findById(id).orElseThrow(()
        -> new EntityNotFoundException(String.format("Professor with ID %s is not found", id)));
    mapperFacade.map(professorRequest, professor);

    professor.setPassword(passwordEncoder.encode(professorRequest.getPassword()));
    professorRepository.save(professor);
    
    return mapperFacade.map(professor, Professor.class);
  }
  
  public void deleteById(@NotNull Long id) {

    boolean professorExists = professorRepository.existsById(id);
    
    if (!professorExists) {
      throw new EntityNotFoundException(String.format("Professor with ID %s is not found", id));
    }
    professorRepository.deleteById(id);
  }
}
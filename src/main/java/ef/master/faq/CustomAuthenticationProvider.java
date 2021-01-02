package ef.master.faq;

import ef.master.faq.entity.Office;
import ef.master.faq.entity.Professor;
import ef.master.faq.entity.Student;
import ef.master.faq.enums.Role;
import ef.master.faq.repository.OfficeRepository;
import ef.master.faq.repository.ProfessorRepository;
import ef.master.faq.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final StudentRepository studentRepository;
  private final ProfessorRepository professorRepository;
  private final OfficeRepository officeRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String email = authentication.getName();
    String password = authentication.getCredentials().toString();

    Student student = studentRepository.findByEmail(email);
    if (student != null) {

      if (!passwordEncoder.matches(password, student.getPassword())) {
        throw new AccessDeniedException("Incorrect password");
      }
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(Role.STUDENT.getRole()));

      return new UsernamePasswordAuthenticationToken(email, password, authorities);
    }

    Professor professor = professorRepository.findByEmail(email);
    if (professor != null) {
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(Role.PROFESSOR.getRole()));

      return new UsernamePasswordAuthenticationToken(email, password, authorities);
    }

    Office office = officeRepository.findByEmail(email);
    if (office != null) {
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(Role.OFFICE.getRole()));

      return new UsernamePasswordAuthenticationToken(email, password, authorities);
    }

    return null;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return aClass.equals(UsernamePasswordAuthenticationToken.class);
  }
}

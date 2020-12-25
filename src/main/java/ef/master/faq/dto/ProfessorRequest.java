package ef.master.faq.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ProfessorRequest {
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;

  @Email
  @NotBlank
  private String email;

  @NotBlank
  private String password;
}

package ef.master.faq.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AssistanceRequest {
  @NotBlank
  private String nameOfService;

  @Email
  @NotBlank
  private String email;

  @NotBlank
  private String password;
}

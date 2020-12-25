package ef.master.faq.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfessorResponse {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}

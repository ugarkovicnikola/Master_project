package ef.master.faq.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssistanceResponse {
  private Long id;
  private String nameOfServices;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}

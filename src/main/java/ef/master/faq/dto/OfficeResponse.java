package ef.master.faq.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OfficeResponse {
  private Long id;
  private String name;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}

package ef.master.faq.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
  private Long id;
  private String text;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private StudentResponse student;
  private ProfessorResponse professor;
  private OfficeResponse office;
  private PostResponse post;
}

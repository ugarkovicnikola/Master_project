package ef.master.faq.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostResponse {
  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private StudentResponse student;
  private Set<TagResponse> tags;
  private Long numberOfComments;
}

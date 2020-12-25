package ef.master.faq.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequest {
  @NotBlank
  private String text;
  @NotNull
  private Long studentId;
//  private Long professorId;
//  private Long serviceId;
  @NotNull
  private Long postId;
}

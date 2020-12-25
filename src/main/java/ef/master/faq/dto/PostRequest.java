package ef.master.faq.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class PostRequest {
  @NotBlank
  private String title;
  @NotBlank
  private String content;
  @NotNull
  private Long studentId;
  @NotNull
  @NotEmpty
  private Set<Long> tagIds;
}

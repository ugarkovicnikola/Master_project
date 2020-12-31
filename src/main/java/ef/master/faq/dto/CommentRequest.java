package ef.master.faq.dto;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequest {
  @NotBlank
  private String text;
  private Long studentId;
  private Long professorId;
  private Long officeId;
  @NotNull
  private Long postId;

  @AssertTrue(message = "You need to fill exactly one ID")
  private boolean assertExactlyOneAuthorExists() {
    int trueStatementsCount =
        (studentId != null ? 1 : 0)
            + (professorId != null  ? 1 : 0)
            + (officeId != null  ? 1 : 0);

    return trueStatementsCount == 1;
  }
}

package ef.master.faq.dto;

import ef.master.faq.enums.VoteEnum;
import lombok.Data;

import javax.validation.constraints.AssertTrue;

@Data
public class VoteRequest {
  private Long studentId;
  private Long professorId;
  private Long officeId;

  private Long postId;
  private Long commentId;

  private VoteEnum vote;

  @AssertTrue(message = "You need to fill exactly one ID")
  private boolean assertExactlyOneAuthorExists() {
    int trueStatementsCount =
        (studentId != null ? 1 : 0)
          + (professorId != null ? 1 : 0)
          + (officeId != null ? 1 : 0);

    return trueStatementsCount == 1;
  }

  @AssertTrue(message = "You need to vote either for post or comment")
  private boolean assertVoteToPostOrComment() {
    int trueStatementCount =
        (postId != null ? 1 : 0)
        + (commentId != null ? 1 : 0);

    return trueStatementCount == 1;
  }
}

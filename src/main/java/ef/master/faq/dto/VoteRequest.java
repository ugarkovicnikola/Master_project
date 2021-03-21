package ef.master.faq.dto;

import ef.master.faq.enums.Vote;
import lombok.Data;

import javax.validation.constraints.AssertTrue;

@Data
public class VoteRequest {
  private Long postId;
  private Long commentId;

  private Vote vote;

  @AssertTrue(message = "You need to vote either for post or comment")
  private boolean assertVoteToPostOrComment() {
    int trueStatementCount =
        (postId != null ? 1 : 0)
        + (commentId != null ? 1 : 0);

    return trueStatementCount == 1;
  }
}

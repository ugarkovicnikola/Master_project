package ef.master.faq.service;

import ef.master.faq.dto.VoteRequest;
import ef.master.faq.enums.VoteEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
@Validated
public class VoteService {

  private final PostService postService;
  private final CommentService commentService;

  public void vote(@NotNull @Valid VoteRequest voteRequest) {
    Long postId = voteRequest.getPostId();
    Long commentId = voteRequest.getCommentId();
    VoteEnum voteEnum = voteRequest.getVote();

   if (VoteEnum.UPVOTE.equals(voteEnum)) {
      if (postId != null) {
        postService.upVote(postId);
      } else {
        commentService.commentUpvote(commentId);
      }
    } else {
      if (postId != null) {
        postService.downVote(postId);
      } else {
        commentService.commentDownvote(commentId);
      }
    }
  }
}

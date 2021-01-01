package ef.master.faq.controller;

import ef.master.faq.dto.VoteRequest;
import ef.master.faq.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteController {

  private final VoteService voteService;

  @PutMapping
  public void vote(@RequestBody VoteRequest voteRequest) {
    voteService.vote(voteRequest);
  }
}

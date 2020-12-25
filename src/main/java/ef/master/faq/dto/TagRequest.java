package ef.master.faq.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TagRequest {
  @NotBlank
  private String name;
}

package ef.master.faq.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
  private Long totalItems;
  private Long pageNumber;
  private Long pageSize;
  private List<T> items;
}

package ef.master.faq.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Office {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  @ToString.Exclude
  private String password;

  @OneToMany(mappedBy = "office")
  private List<Comment> comments;

  @CreationTimestamp
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime createdAt;

  @CreationTimestamp
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime updatedAt;
}

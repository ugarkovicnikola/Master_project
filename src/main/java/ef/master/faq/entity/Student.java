package ef.master.faq.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
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
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;
  private String email;
  private String password;

  @OneToMany(mappedBy = "student")
  private List<Post> posts;

  @OneToMany(mappedBy = "student")
  private List<Comment> comments;

  @CreationTimestamp
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime createdAt;

  @CreationTimestamp
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime updatedAt;
}

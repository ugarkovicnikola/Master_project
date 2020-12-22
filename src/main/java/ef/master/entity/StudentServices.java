package ef.master.entity;

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
public class StudentServices{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nameOfService;
  private String email;
  private String password;

  @OneToMany(mappedBy = "studentServices")
  private List<Comment> comments;

  @CreationTimestamp
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime createdAt;

  @CreationTimestamp
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime updatedAt;
}

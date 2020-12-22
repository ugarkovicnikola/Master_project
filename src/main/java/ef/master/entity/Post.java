package ef.master.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String content;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  @OneToMany(mappedBy = "post")
  private List<Comment> comments;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private List<Tag> tags = new ArrayList<>();

  @CreationTimestamp
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime updatedAt;
}

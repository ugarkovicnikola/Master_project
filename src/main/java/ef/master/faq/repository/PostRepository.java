package ef.master.faq.repository;

import ef.master.faq.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findByStudentId(Long id);
}

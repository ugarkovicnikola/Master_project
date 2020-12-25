package ef.master.faq.repository;

import ef.master.faq.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
  List<Tag> findAllByIdIn(Collection<Long> ids);
}
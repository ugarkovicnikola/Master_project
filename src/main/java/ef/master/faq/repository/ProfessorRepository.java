package ef.master.faq.repository;

import ef.master.faq.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
  boolean existsByEmail(String emal);
}

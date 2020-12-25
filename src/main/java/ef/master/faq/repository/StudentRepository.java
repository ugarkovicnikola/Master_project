package ef.master.faq.repository;

import ef.master.faq.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
  boolean existsByEmail(String email);
}

package ef.master.faq.repository;

import ef.master.faq.entity.Assistance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistanceRepository extends JpaRepository<Assistance, Long> {
  boolean existsByEmail(String email);
}

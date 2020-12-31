package ef.master.faq.repository;

import ef.master.faq.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, Long> {
  boolean existsByEmail(String email);
}

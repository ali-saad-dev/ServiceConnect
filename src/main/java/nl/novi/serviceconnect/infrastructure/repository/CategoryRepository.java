package nl.novi.serviceconnect.infrastructure.repository;

import nl.novi.serviceconnect.infrastructure.models.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ServiceCategory, Long> {
}

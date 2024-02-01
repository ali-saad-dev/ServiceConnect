package nl.novi.serviceconnect.repository;

import nl.novi.serviceconnect.models.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ServiceCategory, Long> {
}

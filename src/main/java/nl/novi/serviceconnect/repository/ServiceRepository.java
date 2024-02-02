package nl.novi.serviceconnect.repository;

import nl.novi.serviceconnect.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Query("SELECT c.name AS categoryName, COUNT(s) AS numberOfServices FROM Service s JOIN s.category c GROUP BY c.id")
    List<Object[]> countServicesPerCategory();
}

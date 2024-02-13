package nl.novi.serviceconnect.infrastructure.repository;

import nl.novi.serviceconnect.infrastructure.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Query("SELECT c.name AS categoryName, COUNT(s) AS numberOfServices FROM Service s JOIN s.category c GROUP BY c.id")
    List<Object[]> countServicesPerCategory();

    @Query("SELECT s FROM Service s WHERE (:categoryId IS NULL OR s.category.id = :categoryId) " +
            "AND (:minPrice IS NULL OR s.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR s.price <= :maxPrice)")
    List<Service> searchServicesByPrice(@Param("categoryId") Long categoryId,
                                           @Param("minPrice") Double minPrice,
                                           @Param("maxPrice") Double maxPrice);
}

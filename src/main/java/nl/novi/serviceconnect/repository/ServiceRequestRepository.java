package nl.novi.serviceconnect.repository;

import nl.novi.serviceconnect.models.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
}

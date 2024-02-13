package nl.novi.serviceconnect.infrastructure.repository;

import nl.novi.serviceconnect.infrastructure.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}

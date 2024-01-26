package nl.novi.serviceconnect.repository;

import nl.novi.serviceconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}

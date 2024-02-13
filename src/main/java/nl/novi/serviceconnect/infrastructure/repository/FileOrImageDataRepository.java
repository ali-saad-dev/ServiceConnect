package nl.novi.serviceconnect.infrastructure.repository;

import nl.novi.serviceconnect.infrastructure.models.FileOrImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FileOrImageDataRepository extends JpaRepository<FileOrImageData, Long> {

    Optional<FileOrImageData> findByName(String fileName);
}

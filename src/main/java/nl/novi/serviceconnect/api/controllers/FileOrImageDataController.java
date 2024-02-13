package nl.novi.serviceconnect.api.controllers;

import nl.novi.serviceconnect.core.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.infrastructure.models.FileOrImageData;
import nl.novi.serviceconnect.infrastructure.models.Service;
import nl.novi.serviceconnect.infrastructure.repository.FileOrImageDataRepository;
import nl.novi.serviceconnect.infrastructure.repository.ServiceRepository;
import nl.novi.serviceconnect.core.services.IFileOrImageDataService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
public class FileOrImageDataController {

    private final IFileOrImageDataService fileOfImageDataService;
    private final FileOrImageDataRepository fileOrImageDataRepository;
    private final ServiceRepository serviceRepository;

    public FileOrImageDataController(IFileOrImageDataService fileOrImageDataService, FileOrImageDataRepository fileOrImageDataRepository, ServiceRepository serviceRepository) {
        this.fileOfImageDataService = fileOrImageDataService;
        this.fileOrImageDataRepository = fileOrImageDataRepository;
        this.serviceRepository = serviceRepository;
    }

    @PostMapping("/uploadFileOrImageToService")
    public ResponseEntity<Object> uploadFileOrImage(@RequestParam("file") MultipartFile multipartFile, @RequestParam Long id) throws IOException {
        fileOfImageDataService.uploadFileOrImage(multipartFile, id);
        return ResponseEntity.ok().body("file or image has been uploaded to service with id: " + id);
    }

    @GetMapping("/getFileOrImageOfService/{id}")
    public ResponseEntity<Object> downloadFileOrImage(@PathVariable Long id) {

        byte[] downloadedFile = fileOfImageDataService.downloadFileOrImage(id);

        Optional<Service> service = serviceRepository.findById(id);

        if (service.isEmpty()) throw new RecordNotFoundException("Service not found");

        Optional<FileOrImageData> dbFileOfImage = fileOrImageDataRepository.findById(service.get().getFileOrImageData().getId());

        if (dbFileOfImage.isEmpty()) throw  new RecordNotFoundException("File not found");

        MediaType mediaType = MediaType.valueOf(dbFileOfImage.get().getType());
        return ResponseEntity.ok().contentType(mediaType).body(downloadedFile);

    }
}

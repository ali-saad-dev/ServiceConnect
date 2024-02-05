package nl.novi.serviceconnect.controllers;

import nl.novi.serviceconnect.models.FileOrImageData;
import nl.novi.serviceconnect.models.Service;
import nl.novi.serviceconnect.repository.FileOrImageDataRepository;
import nl.novi.serviceconnect.repository.ServiceRepository;
import nl.novi.serviceconnect.services.IFileOrImageDataService;
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

        Optional<FileOrImageData> dbFileOfImage = fileOrImageDataRepository.findById(service.get().getFileOrImageData().getId());
        MediaType mediaType = MediaType.valueOf(dbFileOfImage.get().getType());
        return ResponseEntity.ok().contentType(mediaType).body(downloadedFile);

    }
}

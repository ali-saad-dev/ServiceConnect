package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.exceptions.FileAlreadyUploadedException;
import nl.novi.serviceconnect.exceptions.FileSizeExceededException;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.models.FileOrImageData;
import nl.novi.serviceconnect.repository.FileOrImageDataRepository;
import nl.novi.serviceconnect.repository.ServiceRepository;
import nl.novi.serviceconnect.utils.FileOrImageDataUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileOrImageDataService implements IFileOrImageDataService {

    private final FileOrImageDataRepository fileOrImageDataRepository;
    private final ServiceRepository serviceRepository;

    public FileOrImageDataService(FileOrImageDataRepository fileOrImageDataRepository, ServiceRepository serviceRepository) {
        this.fileOrImageDataRepository = fileOrImageDataRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void uploadFileOrImage(MultipartFile multipartFile, Long id) throws IOException {
        Optional<nl.novi.serviceconnect.models.Service> serviceOptional = serviceRepository.findById(id);

        if (serviceOptional.isEmpty()) {
            throw new RecordNotFoundException(" Service with ID " + id + " not found");
        }

        nl.novi.serviceconnect.models.Service service = serviceOptional.get();

        long maxSize = 1024 * 1024;
        if (multipartFile.getSize() > maxSize) {
            throw new FileSizeExceededException(multipartFile.getOriginalFilename(), multipartFile.getSize(), maxSize);
        }

        if (isFileAlreadyUploaded(multipartFile.getOriginalFilename())) {
            throw new FileAlreadyUploadedException(multipartFile.getOriginalFilename());
        }

        FileOrImageData fileOrImageData = new FileOrImageData();
        fileOrImageData.setName(multipartFile.getOriginalFilename());
        fileOrImageData.setType(multipartFile.getContentType());
        fileOrImageData.setFileOrImageData(FileOrImageDataUtil.compressFileOrImage(multipartFile.getBytes()));
        fileOrImageData.setService(service);

        FileOrImageData savedFileOrImage = fileOrImageDataRepository.save(fileOrImageData);

        service.setFileOrImageData(fileOrImageData);
        serviceRepository.save(service);

    }

    @Override
    public byte[] downloadFileOrImage(Long id) {
        Optional<nl.novi.serviceconnect.models.Service> service = serviceRepository.findById(id);

        nl.novi.serviceconnect.models.Service service1 = service.get();

        FileOrImageData fileOrImageData = service1.getFileOrImageData();

        return FileOrImageDataUtil.decompressFileOrImage(fileOrImageData.getFileOrImageData());
    }

    private boolean isFileAlreadyUploaded(String fileName) {
        Optional<FileOrImageData> existingFile = fileOrImageDataRepository.findByName(fileName);

        return existingFile.isPresent();
    }
}

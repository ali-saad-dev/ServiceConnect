package nl.novi.serviceconnect.core.services;

import nl.novi.serviceconnect.core.exceptions.FileAlreadyUploadedException;
import nl.novi.serviceconnect.core.exceptions.FileSizeExceededException;
import nl.novi.serviceconnect.core.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.infrastructure.models.FileOrImageData;
import nl.novi.serviceconnect.infrastructure.repository.FileOrImageDataRepository;
import nl.novi.serviceconnect.infrastructure.repository.ServiceRepository;
import nl.novi.serviceconnect.core.utils.FileOrImageDataUtil;
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

        final long maxSize = 1024 * 1024;
        Optional<nl.novi.serviceconnect.infrastructure.models.Service> serviceOptional = serviceRepository.findById(id);

        if (serviceOptional.isEmpty()) {
            throw new RecordNotFoundException(" Service with Id " + id + " not found");
        }

        if (multipartFile.getSize() > maxSize) {
            throw new FileSizeExceededException(multipartFile.getOriginalFilename(), multipartFile.getSize(), maxSize);
        }

        if (isFileAlreadyUploaded(multipartFile.getOriginalFilename())) {
            throw new FileAlreadyUploadedException(multipartFile.getOriginalFilename());
        }

        nl.novi.serviceconnect.infrastructure.models.Service service = serviceOptional.get();

        FileOrImageData fileOrImageData = new FileOrImageData();
        fileOrImageData.setName(multipartFile.getOriginalFilename());
        fileOrImageData.setType(multipartFile.getContentType());
        fileOrImageData.setFileOrImageData(FileOrImageDataUtil.compressFileOrImage(multipartFile.getBytes()));
        fileOrImageData.setService(service);

       fileOrImageDataRepository.save(fileOrImageData);

        service.setFileOrImageData(fileOrImageData);
        serviceRepository.save(service);
    }

    @Override
    public byte[] downloadFileOrImage(Long id) {

        Optional<nl.novi.serviceconnect.infrastructure.models.Service> service = serviceRepository.findById(id);

        if (service.isEmpty()) throw new RecordNotFoundException(" Service with Id " + id + " not found");

        FileOrImageData fileOrImageData = service.get().getFileOrImageData();

        return FileOrImageDataUtil.decompressFileOrImage(fileOrImageData.getFileOrImageData());
    }

    private boolean isFileAlreadyUploaded(String fileName) {
        Optional<FileOrImageData> existingFile = fileOrImageDataRepository.findByName(fileName);

        return existingFile.isPresent();
    }
}

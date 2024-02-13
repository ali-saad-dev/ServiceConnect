package nl.novi.serviceconnect.core.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileOrImageDataService {

    void uploadFileOrImage(MultipartFile multipartFile, Long id) throws IOException;

    byte[] downloadFileOrImage(Long id);
}

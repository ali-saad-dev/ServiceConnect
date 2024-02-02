package nl.novi.serviceconnect.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileOrImageDataService {

    String uploadFileOrImage(MultipartFile multipartFile, Long id) throws IOException;

    byte[] downloadFileOrImage(Long id);
}

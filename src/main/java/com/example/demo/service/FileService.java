package com.example.demo.service;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    Resource loadFileAsResource(String pathName);

    HttpHeaders loadHttpHeaders(Resource resource) throws IOException;

    String uploadFile(MultipartFile file);

    ResponseEntity<byte[]> prepareContent(String fileName, String fileType, String range);
}

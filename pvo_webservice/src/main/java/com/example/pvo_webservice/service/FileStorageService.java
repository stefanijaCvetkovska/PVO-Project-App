package com.example.pvo_webservice.service;

import com.example.pvo_webservice.model.FileStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;


public interface FileStorageService {

    FileStorage store(MultipartFile file) throws IOException;

    FileStorage getFile(String id);

    Stream<FileStorage> getAllFiles();
}

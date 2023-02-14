package com.example.pvo_webservice.service;

import com.example.pvo_webservice.helper.CSVHelper;
import com.example.pvo_webservice.model.FileData;
import com.example.pvo_webservice.model.FileStorage;
import com.example.pvo_webservice.repository.FileStorageRepository;
import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;

    @Override
    public FileStorage store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        List<FileData> objects = CSVHelper.csvToObjectSortedByDateAscending(file.getInputStream());

        // my code start

        List<String[]> csvData = new ArrayList<>();
        String[] header = {"Date", "Name"};
        csvData.add(header);

        for (FileData fd : objects) {
            String[] line = {String.valueOf(fd.getDate()), String.valueOf(fd.getNumber())};
            csvData.add(line);
        }

        File file1 = new File("sorted_" + fileName);
        try (CSVWriter writer = new CSVWriter(new FileWriter(file1))) {
            writer.writeAll(csvData);
        }

        // my code end

        FileStorage fileStorage =
                new FileStorage(file1.getName(),
                        file.getContentType(),
                        Files.readAllBytes(Path.of(String.valueOf(file1))));

        return fileStorageRepository.save(fileStorage);
    }

    @Override
    public FileStorage getFile(String id) {
        return fileStorageRepository.findById(id).get();
    }

    @Override
    public Stream<FileStorage> getAllFiles() {
        return fileStorageRepository.findAll().stream();
    }
}

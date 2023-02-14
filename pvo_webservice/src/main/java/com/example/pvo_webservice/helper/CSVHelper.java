package com.example.pvo_webservice.helper;

import com.example.pvo_webservice.model.FileData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERS = {"Date", "Number"};

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<FileData> csvToObjectSortedByDateAscending(InputStream is){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){

            List<FileData> objects = new ArrayList<FileData>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                FileData fileData = new FileData(LocalDate.parse(csvRecord.get("Date")), Integer.parseInt(csvRecord.get("Number")));
                objects.add(fileData);
            }

            Collections.sort(objects);

            return objects;

        }catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}

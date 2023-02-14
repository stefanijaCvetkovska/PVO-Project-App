package com.example.pvo_webservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class FileData implements Comparable<FileData> {

    private LocalDate date;

    private int number;

    public FileData(LocalDate date, int number){
        this.date = date;
        this.number = number;
    }

    @Override
    public int compareTo(FileData o) {
        return this.getDate().compareTo(o.getDate());
    }
}

package com.example.pvo_webservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name="files")
@NoArgsConstructor
public class FileStorage {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(unique = true)
    private String name;

    private String type;

    @Lob
    private byte[] data;

    public FileStorage(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}

package com.example.pvo_webservice.repository;

import com.example.pvo_webservice.model.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, String> {
}

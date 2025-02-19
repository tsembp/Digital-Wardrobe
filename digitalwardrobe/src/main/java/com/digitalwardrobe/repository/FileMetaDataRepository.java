package com.digitalwardrobe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.digitalwardrobe.models.FileMetaData;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaData, Integer> {
    FileMetaData findByUniqueId(String uniqueId);
    FileMetaData findByObjectName(String objectName);
}

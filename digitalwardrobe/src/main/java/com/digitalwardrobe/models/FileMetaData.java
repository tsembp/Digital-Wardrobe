package com.digitalwardrobe.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "files")
public class FileMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "unique_id", nullable = false)
    private String uniqueId;

    @Column(name = "object_name", nullable = false)
    private String objectName;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    public FileMetaData() {}

    public FileMetaData(Integer id, String uniqueId, String objectName, LocalDateTime uploadDate) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.objectName = objectName;
        this.uploadDate = uploadDate;
    }

    public void setUniqueId(String uniqueID2) {
        this.uniqueId = uniqueID2;
    }

    public void setObjectName(String objectName2) {
        this.objectName = objectName2;
    }

    public void setUploadDate(LocalDateTime now) {
        this.uploadDate = now;
    }

    public Integer getId() {
        return id;
    }

    public String getUniqueId() {
    return uniqueId;
    }

    public String getObjectName() {
        return objectName;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

}
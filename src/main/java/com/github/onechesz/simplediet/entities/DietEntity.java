package com.github.onechesz.simplediet.entities;

import com.github.onechesz.simplediet.dto.dtoo.DietDTOO;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "diet")
public class DietEntity {
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "file_path", nullable = false)
    private String filePath;
    @Column(name = "file_type", nullable = false)
    private String fileType;
    @Column(name = "file_size", nullable = false)
    private long fileSize;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "duration", nullable = false)
    private int duration;

    @Contract(pure = true)
    public DietEntity() {
    }

    @Contract(pure = true)
    public DietEntity(String fileName, String filePath, String fileType, long fileSize, String title, String description, int duration) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    @Contract(pure = true)
    public DietEntity(int id, String fileName, String filePath, String fileType, long fileSize, String title, String description, int duration) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.title = title;
        this.duration = duration;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull DietDTOO convertToDietDTOO(@NotNull DietEntity dietEntity) {
        return new DietDTOO(dietEntity.id, dietEntity.fileName, dietEntity.title, dietEntity.description, dietEntity.duration);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

package com.github.onechesz.simplediet.dto.dtio;

import com.github.onechesz.simplediet.entities.DietEntity;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

public class DietDTIO {
    public final static String UPLOADS_IMAGES_PATH = System.getProperty("user.dir") + "/uploads/images/";
    private MultipartFile multipartFile;
    @Size(min = 4, max = 256, message = "Количество символов в названии должно варьироваться от 4-х до 256-ти.")
    private String title;
    @Size(min = 128, max = 1023, message = "Количество символов в описании должно варьироваться от 128-ми до 1023-х.")
    private String description;
    @Positive(message = "Продолжительность должна быть положительным числом.")
    private int duration;

    @Contract(pure = true)
    public DietDTIO() {

    }

    @Contract(pure = true)
    public DietDTIO(MultipartFile multipartFile, String title, String description, int duration) {
        this.multipartFile = multipartFile;
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    public static @NotNull DietEntity convertToDietEntity(@NotNull DietDTIO dietDTIO) {
        String originalFileName = dietDTIO.multipartFile.getOriginalFilename();

        return new DietEntity(
                originalFileName,
                Paths.get(UPLOADS_IMAGES_PATH, originalFileName).toString(),
                dietDTIO.multipartFile.getContentType(),
                dietDTIO.multipartFile.getSize(),
                dietDTIO.title,
                dietDTIO.description,
                dietDTIO.duration
        );
    }

    public static @NotNull DietEntity convertToDietEntity(@NotNull DietDTIO dietDTIO, int id) {
        String originalFileName = dietDTIO.multipartFile.getOriginalFilename();

        return new DietEntity(
                id,
                originalFileName,
                Paths.get(UPLOADS_IMAGES_PATH, originalFileName).toString(),
                dietDTIO.multipartFile.getContentType(),
                dietDTIO.multipartFile.getSize(),
                dietDTIO.title,
                dietDTIO.description,
                dietDTIO.duration
        );
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
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

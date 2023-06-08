package com.github.onechesz.simplediet.dto.dtoo;

import org.jetbrains.annotations.Contract;

public class DietDTOO {
    private int id;
    private String fileName;
    private String title;
    private String description;
    private int duration;

    @Contract(pure = true)
    public DietDTOO() {

    }

    @Contract(pure = true)
    public DietDTOO(int id, String fileName, String title, String description, int duration) {
        this.id = id;
        this.fileName = fileName;
        this.title = title;
        this.description = description;
        this.duration = duration;
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

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

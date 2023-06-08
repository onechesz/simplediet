package com.github.onechesz.simplediet.dto;

import com.github.onechesz.simplediet.entities.UserParametersEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserParametersDTO {
    private String sex;
    @Min(value = 6, message = "Минимальный возраст для получения диеты - 6 лет.")
    @Max(value = 120, message = "Введите корректный возраст.")
    private int age;
    @Min(value = 100, message = "Минимальный рост для получения диеты - 100см.")
    @Max(value = 250, message = "Введите корректный рост.")
    private int height;
    @Min(value = 20, message = "Минимальный вес для получения диеты - 20кг.")
    @Max(value = 250, message = "Введите корректный вес.")
    private int weight;
    private List<String> allergy;
    private String physicalActivity;

    @Contract(pure = true)
    public UserParametersDTO() {

    }

    @Contract(pure = true)
    public UserParametersDTO(String sex, int age, int height, int weight, List<String> allergy, String physicalActivity) {
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.allergy = allergy;
        this.physicalActivity = physicalActivity;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull UserParametersEntity convertToUserParametersEntity(@NotNull UserParametersDTO userParametersDTO) {
        return new UserParametersEntity(userParametersDTO.sex, userParametersDTO.age, userParametersDTO.height, userParametersDTO.weight, String.join(", ", userParametersDTO.allergy), userParametersDTO.physicalActivity);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<String> getAllergy() {
        return allergy;
    }

    public void setAllergy(List<String> allergy) {
        this.allergy = allergy;
    }

    public String getPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(String physicalActivity) {
        this.physicalActivity = physicalActivity;
    }
}

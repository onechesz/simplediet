package com.github.onechesz.simplediet.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.jetbrains.annotations.Contract;

@Entity
@Table(name = "user_parameters")
public class UserParametersEntity {
    @Column(name = "user_id", nullable = false)
    @Id
    private int userId;
    @Column(name = "sex", nullable = false)
    private String sex;
    @Column(name = "age", nullable = false)
    @Min(value = 6, message = "Минимальный возраст для получения диеты - 6 лет.")
    @Max(value = 120, message = "Введите корректный возраст.")
    private int age;
    @Column(name = "height", nullable = false)
    @Min(value = 100, message = "Минимальный рост для получения диеты - 100см.")
    @Max(value = 250, message = "Введите корректный рост.")
    private int height;
    @Column(name = "weight", nullable = false)
    @Min(value = 20, message = "Минимальный вес для получения диеты - 20кг.")
    @Max(value = 250, message = "Введите корректный вес.")
    private int weight;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Contract(pure = true)
    public UserParametersEntity() {

    }

    @Contract(pure = true)
    public UserParametersEntity(String sex, int age, int height, int weight) {
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}

package com.github.onechesz.simplediet.entities;

import com.github.onechesz.simplediet.dto.UserParametersDTO;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity
@Table(name = "user_parameters")
public class UserParametersEntity {
    @Column(name = "user_id", nullable = false)
    @Id
    private int userId;
    @Column(name = "sex", nullable = false)
    private String sex;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "height", nullable = false)
    private int height;
    @Column(name = "weight", nullable = false)
    private int weight;
    @Column(name = "allergy")
    private String allergy;
    @Column(name = "physical_activity")
    private String physicalActivity;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Contract(pure = true)
    public UserParametersEntity() {

    }

    @Contract(pure = true)
    public UserParametersEntity(String sex, int age, int height, int weight, String allergy, String physicalActivity) {
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.allergy = allergy;
        this.physicalActivity = physicalActivity;
    }

    @Contract("_ -> new")
    public static @NotNull UserParametersDTO convertToUserParametersDTO(@NotNull UserParametersEntity userParametersEntity) {
        return new UserParametersDTO(userParametersEntity.sex, userParametersEntity.age, userParametersEntity.height, userParametersEntity.weight, List.of(userParametersEntity.allergy.split(", ")), userParametersEntity.physicalActivity);
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

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(String physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}

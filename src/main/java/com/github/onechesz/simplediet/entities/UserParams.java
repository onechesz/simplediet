package com.github.onechesz.simplediet.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_params")
public class UserParams {
    @Column(name = "user_id")
    @Id
    private int userId;
    @Column(name = "sex")
    private String sex;
    @Column(name = "age")
    private int age;
    @Column(name = "height")
    private int height;
    @Column(name = "weight")
    private int weight;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public UserParams() {

    }

    public UserParams(String sex, int age, int height, int weight) {
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

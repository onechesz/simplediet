package com.github.onechesz.simplediet.entities;

import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.time.LocalDateTime;

@Entity
@Table(name = "personal_diet")
public class PersonalDietEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @Column(name = "meal")
    private String meal;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "days_duration")
    private int daysDuration;
    @Column(name = "completion_reason")
    private String completionReason;
    @Column(name = "status")
    private String status;
    @OneToOne(mappedBy = "personalDietEntity")
    private UserEntity currentUserEntity;

    @Contract(pure = true)
    public PersonalDietEntity() {

    }

    @Contract(pure = true)
    public PersonalDietEntity(UserEntity userEntity, int daysDuration, String status) {
        this.userEntity = userEntity;
        this.daysDuration = daysDuration;
        this.status = status;
    }

    @Contract(pure = true)
    public PersonalDietEntity(int id, UserEntity userEntity, String meal, LocalDateTime startTime, int daysDuration, String completionReason, String status, UserEntity currentUserEntity) {
        this.id = id;
        this.userEntity = userEntity;
        this.meal = meal;
        this.startTime = startTime;
        this.daysDuration = daysDuration;
        this.completionReason = completionReason;
        this.status = status;
        this.currentUserEntity = currentUserEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getDaysDuration() {
        return daysDuration;
    }

    public void setDaysDuration(int daysDuration) {
        this.daysDuration = daysDuration;
    }

    public String getCompletionReason() {
        return completionReason;
    }

    public void setCompletionReason(String completionReason) {
        this.completionReason = completionReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserEntity getCurrentUserEntity() {
        return currentUserEntity;
    }

    public void setCurrentUserEntity(UserEntity currentUserEntity) {
        this.currentUserEntity = currentUserEntity;
    }
}

package com.github.onechesz.simplediet.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.Contract;

import java.util.Set;

@Entity
@Table(name = "\"user\"")
public class UserEntity {
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", nullable = false)
    @Size(min = 4, max = 256, message = "Имя пользователя должно содержать не менее 4-х символов.")
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Transient
    @Size(min = 7, message = "Пароль должен состоять как минимум из 7-ми символов.")
    @Size(max = 32, message = "Пароль должен состоять максимум из 32-х символов.")
    private String userPassword;
    @Transient
    private String confirmedPassword;
    @Column(name = "role", nullable = false)
    private String role;
    @OneToOne(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserParametersEntity userParametersEntity;
    @OneToOne
    @JoinColumn(name = "personal_diet_id", referencedColumnName = "id")
    private PersonalDietEntity personalDietEntity;
    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<PersonalDietEntity> personalDietEntities;

    @Contract(pure = true)
    public UserEntity() {

    }

    @Contract(pure = true)
    public UserEntity(int id) {
        this.id = id;
    }

    @Contract(pure = true)
    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Contract(pure = true)
    public UserEntity(int id, String username, String password, String userPassword, String confirmedPassword, String role, UserParametersEntity userParametersEntity, PersonalDietEntity personalDietEntity, Set<PersonalDietEntity> personalDietEntities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userPassword = userPassword;
        this.confirmedPassword = confirmedPassword;
        this.role = role;
        this.userParametersEntity = userParametersEntity;
        this.personalDietEntity = personalDietEntity;
        this.personalDietEntities = personalDietEntities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserParametersEntity getUserParametersEntity() {
        return userParametersEntity;
    }

    public void setUserParametersEntity(UserParametersEntity userParametersEntity) {
        this.userParametersEntity = userParametersEntity;
    }

    public PersonalDietEntity getPersonalDietEntity() {
        return personalDietEntity;
    }

    public void setPersonalDietEntity(PersonalDietEntity personalDietEntity) {
        this.personalDietEntity = personalDietEntity;
    }

    public Set<PersonalDietEntity> getPersonalDietEntities() {
        return personalDietEntities;
    }

    public void setPersonalDietEntities(Set<PersonalDietEntity> personalDietEntities) {
        this.personalDietEntities = personalDietEntities;
    }
}

package com.github.onechesz.simplediet.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "\"user\"")
public class UserEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    @Size(min = 4, max = 256, message = "Имя пользователя должно содержать не менее 4-х символов.")
    private String username;
    @Column(name = "password")
    private String password;
    @Transient
    @Size(min = 7, message = "Пароль должен состоять как минимум из 7-ми символов.")
    @Size(max = 32, message = "Пароль должен состоять максимум из 32-х символов.")
    private String userPassword;
    @Transient
    private String confirmedPassword;
    @Column(name = "role")
    private String role;
    @OneToMany(mappedBy = "userEntity")
    private List<CartEntity> cartEntities;
    @OneToMany(mappedBy = "userEntity")
    private List<OrderEntity> orderEntities;
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserParams userParams;

    public UserEntity() {

    }

    public UserEntity(int id) {
        this.id = id;
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
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

    public List<CartEntity> getCartEntities() {
        return cartEntities;
    }

    public void setCartEntities(List<CartEntity> cartEntities) {
        this.cartEntities = cartEntities;
    }

    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }

    public UserParams getUserParams() {
        return userParams;
    }

    public void setUserParams(UserParams userParams) {
        this.userParams = userParams;
    }
}

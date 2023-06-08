package com.github.onechesz.simplediet.util.gpt;

import org.jetbrains.annotations.Contract;

public class Message {
    private String role;
    private String content;

    @Contract(pure = true)
    public Message() {
        role = "system";
        content = "You are a nutritionist whose task is to generate personal diets for the user.";
    }

    @Contract(pure = true)
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

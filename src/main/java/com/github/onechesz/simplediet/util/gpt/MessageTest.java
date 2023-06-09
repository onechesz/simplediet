package com.github.onechesz.simplediet.util.gpt;

import org.jetbrains.annotations.Contract;

public class MessageTest {
    private String role;
    private String content;

    @Contract(pure = true)
    public MessageTest() {

    }

    @Contract(pure = true)
    public MessageTest(String role, String content) {
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

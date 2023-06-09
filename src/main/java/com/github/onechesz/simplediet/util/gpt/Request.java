package com.github.onechesz.simplediet.util.gpt;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private String model;
    private List<Message> messages;

    @Contract(pure = true)
    public Request() {
        model = "gpt-3.5-turbo";
        messages = new ArrayList<>(List.of(new Message()));
    }

    @Contract(pure = true)
    public Request(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void appendMessage(Message message) {
        messages.add(message);
    }
}

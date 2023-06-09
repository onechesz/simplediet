package com.github.onechesz.simplediet.util.gpt;

import org.jetbrains.annotations.Contract;

import java.util.List;

public class Response {
    public static class Choice {
        private Message message;

        @Contract(pure = true)
        public Choice() {

        }

        @Contract(pure = true)
        public Choice(Message message) {
            this.message = message;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

    private List<Choice> choices;

    @Contract(pure = true)
    public Response() {

    }

    @Contract(pure = true)
    public Response(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}

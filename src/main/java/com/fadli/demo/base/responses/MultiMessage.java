package com.fadli.demo.base.responses;

import java.util.ArrayList;

public class MultiMessage {
    private String name;
    private ArrayList<ArrayList<Message>> messages;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<ArrayList<Message>> getMessages() {
        return messages;
    }
    public void setMessages(ArrayList<ArrayList<Message>> messages) {
        this.messages = messages;
    }
}

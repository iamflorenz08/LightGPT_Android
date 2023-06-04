package com.example.lightgpt.Models;

import java.util.ArrayList;

public class ConversationModel {
    private String _id;
    private String title;
    private ArrayList<MessageModel> messages;

    public ConversationModel(String _id, String title, ArrayList<MessageModel> messages) {
        this._id = _id;
        this.title = title;
        this.messages = messages;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageModel> messages) {
        this.messages = messages;
    }
}

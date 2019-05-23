package com.pao.project.connection.talkUI;

public class Message {
    String user;
    String message;
    boolean myMessage;

    public Message(String user, String message, boolean myMessage) {
        this.user = user;
        this.message = message;
        this.myMessage = myMessage;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMyMessage() {
        return myMessage;
    }
}

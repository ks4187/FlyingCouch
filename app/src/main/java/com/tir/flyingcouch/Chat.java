package com.tir.flyingcouch;

/**
 * Created by Karan Shah on 4/18/2016.
 */
public class Chat {

    private String message;
    private String sender;

    @SuppressWarnings("unused")
    private Chat(){
    }

    Chat(String message, String sender){
        this.message = message;
        this.sender = sender;
    }

    public String getMessage(){
        return message;
    }

    public String getSender(){
        return sender;
    }
}

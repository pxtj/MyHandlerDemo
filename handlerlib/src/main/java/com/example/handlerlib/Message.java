package com.example.handlerlib;

public class Message {

    public int what;
    public Object obj;
    Handler target;
    public Message() {

    }

    @Override
    public String toString() {
        return obj.toString();
    }
}

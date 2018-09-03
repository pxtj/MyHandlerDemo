package com.example.handlerlib;

public class Handler {
    private Looper mLooper;
    private MessageQueue mQueue;

    public Handler() {
        mLooper = Looper.myLooper();
        mQueue = mLooper.mQueue;
    }

    public void sendMessage(Message msg) {
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }

    public void handleMessage(Message msg) {

    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }
}

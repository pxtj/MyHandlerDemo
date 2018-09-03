package com.example.handlerlib;

import java.util.UUID;

public class HandlerTest {

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
//                sendAndHandle();
            }
        }.start();

        sendAndHandle();
    }

    private static void sendAndHandle() {
        Looper.prepaer();

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                System.out.println("handle = " + Thread.currentThread().getName());
                System.out.println("handle msg = " + msg.toString());
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        Message msg = new Message();
                        synchronized (UUID.class) {
                            msg.obj = UUID.randomUUID().toString();
                        }
                        msg.what = 1;
                        System.out.println("send = " + Thread.currentThread().getName());
                        System.out.println("send msg = " + msg.toString());
                        handler.sendMessage(msg);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

        }
        Looper.loop();
    }
}

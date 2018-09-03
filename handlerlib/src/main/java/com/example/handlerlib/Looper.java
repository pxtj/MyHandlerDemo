package com.example.handlerlib;

public class Looper {

    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    MessageQueue mQueue;

    private Looper() {
        mQueue = new MessageQueue();
    }

    /**
     * init looper. must init in main thread;
     */
    public static void prepaer() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("only one ");
        }

        sThreadLocal.set(new Looper());
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    /**
     * loop to handle msg, run in main thread;
     */
    public static void loop() {
        Looper looper = Looper.myLooper();
        if (looper == null) {
            throw new RuntimeException("no loop");
        }

        MessageQueue queue = looper.mQueue;
        for (; ; ) {
            Message msg = queue.next();
            if (msg == null) {
                continue;
            }

            msg.target.dispatchMessage(msg);
        }
    }
}

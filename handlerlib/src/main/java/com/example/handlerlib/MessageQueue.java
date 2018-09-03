package com.example.handlerlib;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 队列有一个大小，50个
 * 队列如果满了，会阻塞
 * 如果队列为空，也会阻塞；
 */
public class MessageQueue {

    private Message[] items;
    private static final int CAPACITY = 50;
    // enqueue and dequeue index;
    private int putIndex;
    private int takeIndex;

    private int count;
    private Lock lock;
    private Condition notEmpty;
    private Condition notFull;

    public MessageQueue() {
        items = new Message[CAPACITY];
        lock = new ReentrantLock(); //ke chongrusuo;
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    /**
     * run in subthread. producer;
     * @param msg
     */
    public void enqueueMessage(Message msg) {
        try {
            lock.lock();
            // message queue is full, wait;
            while (count == CAPACITY) {
                notFull.await();
            }
            items[putIndex] = msg;
            putIndex = (++putIndex == items.length) ? 0 : putIndex;
            count++;
            // enqueue is not empty, notify subthread to consume
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * main thread. consumer;
     * @return
     */
    public Message next() {
        Message msg = null;
        try {
            lock.lock();
            while (count == 0) {
                notEmpty.await();
            }
            msg = items[takeIndex];
            items[takeIndex] = null; // set null when take;
            takeIndex = (++takeIndex == items.length) ? 0 : takeIndex;
            count--;
            // enqueue is not full, notify subthread to product
            notFull.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return msg;
    }
}

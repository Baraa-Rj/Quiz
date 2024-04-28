package com.example.baraarj;

import java.util.Random;

public class Cashier {
    private QueueUsingStack<Integer> queue = new QueueUsingStack<>();
    private int totalInQueue = 0;
    private int processedCustomers = 0;
    private int min = 0;


    public void addCustomer() {
        Random random = new Random(4);
        int newCustomers = random.nextInt(4);
        if (newCustomers == 0 || newCustomers == 3) {
            return;
        } else if (newCustomers == 2) {
            queue.enqueue(1);
            queue.enqueue(1);
            totalInQueue += 2;
        } else {
            queue.enqueue(1);
            totalInQueue++;
        }
        min++;
    }

    public void processCustomer() {
        if (!queue.isEmpty()) {
            queue.dequeue();
            totalInQueue--;
            processedCustomers++;
        }
    }

    public void simulate(int time) {
        for (int i = 0; i < time; i++) {
            addCustomer();
            processCustomer();
        }
        while (!queue.isEmpty()) {
            queue.dequeue();
            totalInQueue--;
            min++;
        }
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getProcessedCustomers() {
        return processedCustomers;
    }

    public void setProcessedCustomers(int processedCustomers) {
        this.processedCustomers = processedCustomers;
    }

    public QueueUsingStack<Integer> getQueue() {
        return queue;
    }

    public void setQueue(QueueUsingStack<Integer> queue) {
        this.queue = queue;
    }


    public int getTotalInQueue() {
        return totalInQueue;
    }

    public void setTotalInQueue(int totalInQueue) {
        this.totalInQueue = totalInQueue;
    }
}

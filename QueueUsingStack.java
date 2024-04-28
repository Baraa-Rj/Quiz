package com.example.baraarj;

public class QueueUsingStack<T extends Comparable<T>> {
    private LinkedStack<T> stack1;

    public QueueUsingStack() {
        stack1 = new LinkedStack<>();
    }

    public void enqueue(T data) {
        stack1.push(data);
    }

    public Node<T> dequeue() {
        LinkedStack<T> stack2 = new LinkedStack<>();
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop().getData());
        }
        Node<T> toDel = stack2.pop();
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop().getData());
        }
        return toDel;
    }

    public Node<T> getFront() {
        return stack1.peek();
    }

    public boolean isEmpty() {
        return stack1.isEmpty();
    }

    public void clear() {
        stack1.clear();
    }

    public void traverse() {
        for (T data : stack1) {
            System.out.println(data);
        }
    }

    public int size() {
        int count = 0;
        for (T data : stack1) {
            count++;
        }
        return count;
    }

}

package com.example.baraarj;

public class StackUsingLinkedList<T extends Comparable<T>> {

    private final DLinkedList<T> list = new DLinkedList<>();
    private int size = 0;

    public void push(T data) {
        list.insert(data);
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T data = list.getHead().getData();
        if (data == null) {
            throw new IllegalStateException("Stack is corrupted: head data is null");
        }
        list.delete(data);
        size--;
        return data;
    }


    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return list.getHead().getNext().getData();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    public int size() {
        return size;
    }
}

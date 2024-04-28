package com.example.baraarj;

public class CNode<T extends Comparable<T>> {
    T data;
    int next;
    public CNode(T data, int next){
        this.data=data;
        this.next=next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }
}

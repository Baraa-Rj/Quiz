package com.example.baraarj;

public interface Listable<T extends Comparable<T>> {
    void add(T data);

    boolean delete(T data);

    boolean find(T data);

    void clear();

    boolean isEmpty();

    int size();

    void traverse();


    boolean set(int index, T data);

    T get(int index);
}

package com.example.baraarj;

public class MyList<T extends Comparable<T>> implements Listable<T> {
    T[] list;
    int count;

    public MyList() {
        list = (T[]) new Comparable[10];
        count = 0;
    }

    MyList(int size) {
        list = (T[]) new Comparable[size];
        count = 0;
    }

    public MyList(T[] list, int count) {
        this.list = list;
        this.count = count;
    }

    @Override
    public void add(T data) {
        if (count != list.length) {
            list[count] = data;
            count++;
        } else {
            resize();
            list[count] = data;
            count++;
        }
    }

    public void resize() {
        T[] res = (T[]) new Comparable[2 * list.length];
        for (int i = 0; i < list.length; i++) {
            res[i] = list[i];
        }
        list = res;

    }

    @Override
    public boolean delete(T data) {
        for (int i = 0; i < count; i++) {
            if (list[i] == data) {
                for (int j = i + 1; j < count; j++) {
                    list[j - 1] = list[j];
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean find(T data) {
        if (data != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].compareTo(data) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        count = 0;
    }

    @Override
    public boolean isEmpty() {
        return list.length == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void traverse() {
        for (int i = 0; i < count; i++) {
            if (list[i] != null) {
                System.out.println(i + " " + list[i]);
            }
        }
    }


    @Override
    public boolean set(int index, T data) {
        if (data != null && index < list.length - 1) {
            list[index] = data;
            return true;
        }
        return false;
    }

    @Override
    public T get(int index) {
        if (index > count - 1) {
            System.out.println("Index out of bounds");
        }
        return list[index];

    }
}


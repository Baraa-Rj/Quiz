package com.example.baraarj;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LinkedList<T extends Comparable<T>>  {
    Node<T> head;

    public LinkedList() {
        this.head = new Node<>(null);

    }

    public int length() {
        int length = 0;
        Node<T> current = head;
        while (current.getNext() != null) {
            length++;
            current = current.getNext();
        }
        return length;
    }

    public LinkedList(Node<T> head) {
        this.head = head;
    }

    public void insert(T data) {
        Node<T> newNode = new Node<>(data);
        Node<T> current = head;
        for (; current.getNext() != null && current.getNext().getData().compareTo(data) < 0;
             current = current.getNext())
            ;
        newNode.setNext(current.getNext());
        current.setNext(newNode);

    }

    public void delete(T data) {
        Node<T> current = head;
        for (; current.getNext() != null && current.getNext().getData().compareTo(data) != 0;
             current = current.getNext())
            ;
        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
        }
    }

    public Node<T> find(T data) {
        Node<T> current = head.getNext();
        for (; current != null && current.getData().compareTo(data) != 0; current = current.getNext())
            ;
        return current;
    }

    public String traverse() {
        String res = "Head-->";
        Node<T> current = this.head;
        while (current != null) {
            res += "[" + current + "]";
            current = current.getNext();
            if (current != null) {
                res += "-->";
            }
        }
        return res + "-->null";
    }

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }
}


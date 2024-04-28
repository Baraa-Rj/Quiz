package com.example.baraarj;

import static java.lang.System.out;

public class DLinkedList<T extends Comparable<T>> {
    private DNode<T> head;
    private DNode<T> current;

    public DLinkedList() {
        head = new DNode<>(null);
        head.setNext(null);
        head.setPrev(null);
        current = head;
    }

    @Override
    public String toString() {
        String res = "Head-->";
        DNode<T> current = this.head;
        while (current != null) {
            res += "[" + current + "]";
            current = current.getNext();
            if (current != null) {
                res += "<=>";
            }
        }
        return res + "-->null";
    }

    public void insert(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the list");
        }
        DNode<T> current = head;
        while (current.getNext() != null && current.getNext().getData().compareTo(data) < 0) {
            current = current.getNext();
        }
        DNode<T> temp = new DNode<>(data);
        temp.setNext(current.getNext());
        if (current.getNext() != null) {
            current.getNext().setPrev(temp);
        }
        current.setNext(temp);
        temp.setPrev(current);
    }

    public DNode<T> delete(T data) {
        if (data == null) {
            return null;
        }
        DNode<T> current = head.getNext();
        while (current != null && !current.getData().equals(data)) {
            current = current.getNext();
        }
        if (current != null) {
            if (current.getPrev() != null) {
                current.getPrev().setNext(current.getNext());
            }
            if (current.getNext() != null) {
                current.getNext().setPrev(current.getPrev());
            }
            return current;
        }
        return null;
    }


    public DNode<T> find(T data) {
        DNode<T> current = head.getNext();
        for (; current != null && current.getData().compareTo(data) != 0; current = current.getNext()) ;
        return current;
    }


    public void traverse() {
        DNode<T> current = head.getNext();
        while (current != null) {
            out.print(current.getData() + " ");
            current = current.getNext();
        }
    }

    public int length() {
        DNode<T> current = head.getNext();
        int size = 0;
        while (current != null) {
            size++;
            current = current.getNext();
        }
        return size;
    }

    public int lengthWithRecursion(DNode<T> current) {
        if (current.getNext() == null)
            return 0;
        else {
            return 1 + lengthWithRecursion(current.getNext());
        }
    }

    public DNode<T> getHead() {
        return head;
    }

    public void setHead(DNode<T> head) {
        this.head = head;
    }

    public void traverseWithIterative() {
        DNode<T> current = head;
        for (; current.getNext() != null; current = current.getNext()) ;
        while (current != null) {
            out.println(current.getData());
            current = current.getPrev();
        }
    }

    private void printInReverseOrder(DNode<T> node) {
        if (node != null) {
            printInReverseOrder(node.getNext());
            out.print(node.getData() + " ");
        }
    }

    public void printInReverseOrder() {
        printInReverseOrder(head);
    }

    public int countIterative(T data) {
        DNode<T> current = head.getNext();
        int count = 0;
        while (current != null) {
            if (current.getData().compareTo(data) == 0) {
                count++;
            }
            current = current.getNext();
        }
        return count;
    }

    public int countRecursive(T data, DNode<T> node) {
        if (node.getNext() == null || node.getData().compareTo(data) > 0)
            return 0;
        else if (node.getNext().getData().compareTo(data) == 0)
            return 1 + countRecursive(data, node.getNext());
        else
            return countRecursive(data, node.getNext());
    }

    public void removeDuplicate() {
        DNode<T> current = head.getNext();
        while (current != null) {
            if (current.getNext() != null && current.getData().compareTo(current.getNext().getData()) == 0) {
                current.setNext(current.getNext().getNext());
                if (current.getNext() != null) {
                    current.getNext().setPrev(current);
                }
            } else {
                current = current.getNext();
            }
        }
    }

    public void removeDuplicatesWithRecursion(DNode<T> node, DNode prev) {
        if (node == null) {
            return;
        }
        if (prev != null && node.getData() == prev.getData()) {
            prev.setNext(node.getNext());
            if (node.getNext() != null) {
                node.getNext().setPrev(prev);
            }
            removeDuplicatesWithRecursion(node.getNext(), prev);
        } else {
            removeDuplicatesWithRecursion(node.getNext(), node);
        }

    }

    public DNode<T> reverseDoublyLinkedList(DNode<T> head) {
        DNode<T> current = head;
        DNode<T> temp = null;
        while (current != null) {
            temp = current.getPrev();
            current.setPrev(current.getNext());
            current.setNext(temp);
            current = current.getPrev();
        }
        if (temp != null) {
            head = temp.getPrev();
        }
        return head;
    }

    public DNode<T> getCurrent() {
        return current;
    }

    public void setCurrent(DNode<T> current) {
        this.current = current;
    }

    public DNode<T> toNextNode() {
        if (current.getNext() != null) {
            current = current.getNext();
            return current;
        }
        return null;
    }

    public DNode<T> toPrevNode() {
        if (current.getPrev() != null) {
            current = current.getPrev();
            return current;
        }
        return null;
    }
}

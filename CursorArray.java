package com.example.baraarj;

import static java.lang.System.out;

public class CursorArray<T extends Comparable<T>> {
    CNode<T>[] cursorArray;

    public CursorArray(int length) {
        this.cursorArray = new CNode[length];
        intitialization();
    }

    public int intitialization() {
        for (int i = 0; i < cursorArray.length; i++)
            cursorArray[i] = new CNode<>(null, i + 1);
        cursorArray[cursorArray.length - 1] = new CNode<>(null, 0);
        return 0;
    }

    public int malloc() {
        int p = cursorArray[0].next;
        cursorArray[0].next = cursorArray[p].next;
        return p;
    }

    public void free(int p) {
        cursorArray[p] = new CNode<>(null, cursorArray[0].next);
        cursorArray[0].next = p;
    }

    public boolean isNull(int l) {
        return cursorArray[l] != null;
    }

    public boolean isEmpty(int l) {
        return cursorArray[l].next == 0;
    }

    public boolean isLast(int p) {
        return cursorArray[p].next == 0;
    }

    public int createList() {
        int l = malloc();
        if (l == 0)
            out.println("Memory is Full");
        else
            cursorArray[l] = (CNode<T>) new CNode<>("", 0);
        return l;
    }

    public void insertAtHead(T data, int l) {
        insertion(data, l);
    }

    public void traverseList(int l) {
        out.print("List_" + "|" + "--> ");
        while (!isEmpty(l) && isNull(l)) {
            l = cursorArray[l].next;
            out.print(cursorArray[l].data + "-->");
        }
        out.println("null");
    }

    public int find(T data, int l) {
        while (!isEmpty(l) && isNull(l)) {
            l = cursorArray[l].next;
            if (cursorArray[l].data.equals(data))
                return l;
        }
        return -1;
    }

    public int findPrev(T data, int l) {
        while (!isEmpty(l) && isNull(l)) {
            if (cursorArray[cursorArray[l].next].data.equals(data))
                return l;
            l = cursorArray[l].next;
        }
        return -1;
    }

    public void insertAtLast(T data, int l) {
        while (!isLast(l) && isNull(l))
            l = cursorArray[l].next;
        insertion(data, l);
    }

    public void insertAfter(T data, T after, int l) {
        int p = find(after, l);
        if (p == -1)
            out.println("Data not found");
        else
            insertion(data, p);
    }

    private void insertion(T data, int l) {
        int p = malloc();
        if (p != 0) {
            cursorArray[p].data = data;
            cursorArray[p].next = cursorArray[l].next;
            cursorArray[l].next = p;
            out.println("Inserted!");
        } else {
            out.println("it's full!");
        }
    }

    public CNode<T> delete(T data, int l) {
        int p = find(data, l);
        if (p == -1) {
            out.println("Data not found");
            return null;
        } else {
            int q = findPrev(data, l);
            cursorArray[q].next = cursorArray[p].next;
            free(p);
            return cursorArray[p];
        }
    }

    public void traverse() {
        for (CNode<T> node : cursorArray) {
            if (node != null)
                out.println(node.data);
        }
    }

    public void traverseRecursively(int l) {
        if (l == 0)
            return;
        out.println(cursorArray[l].data);
        traverseRecursively(cursorArray[l].next);
    }

    public int lengthIterativeForList(int l) {
        int count = 0;
        while (!isEmpty(l) && isNull(l)) {
            l = cursorArray[l].next;
            count++;
        }
        return count;
    }

    public int lengthRecursionForList(int l) {
        if (l == 0)
            return 0;
        return 1 + lengthRecursionForList(cursorArray[l].next);
    }

    public void clearListWithRecursion(int l) {
        if (l == 0)
            return;
        clearListWithRecursion(cursorArray[l].next);
        free(l);
    }

    public void insertSorted(T data, int l) {
        int p = l;
        int q = cursorArray[l].next;
        while (q != 0 && cursorArray[q].data.compareTo(data) < 0) {
            p = q;
            q = cursorArray[q].next;
        }
        insertion(data, p);
    }

    public int mergeSortedLists(int l, int p) {
        int l1 = createList();
        int i = cursorArray[l].next;
        int j = cursorArray[p].next;
        while (i != 0 && j != 0) {
            if (cursorArray[i].data.compareTo(cursorArray[j].data) < 0) {
                insertAtLast(cursorArray[i].data, l1);
                i = cursorArray[i].next;
            } else {
                insertAtLast(cursorArray[j].data, l1);
                j = cursorArray[j].next;
            }
        }
        while (i != 0) {
            insertAtLast(cursorArray[i].data, l1);
            i = cursorArray[i].next;
        }
        while (j != 0) {
            insertAtLast(cursorArray[j].data, l1);
            j = cursorArray[j].next;
        }
        return l1;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CNode<T> node : cursorArray) {
            if (node != null)
                sb.append(node.data).append(" ");
        }
        return sb.toString();
    }

}

package com.example.baraarj;

public class Quiz {

    static StackUsingLinkedList<Integer> stack = new StackUsingLinkedList<>();

    public static void main(String[] args) {
        pushSorted(50, stack);
        pushSorted(20, stack);
        pushSorted(70, stack);
        pushSorted(40, stack);
        pushSorted(80, stack);

        printBackWord(stack);
    }


    // O(N)
    private static void pushSorted(int data, StackUsingLinkedList<Integer> stack) {
        if (stack.isEmpty() || stack.peek().compareTo(data) < 0) {
            stack.push(data);
        } else {
            int temp = stack.pop();
            pushSorted(data, stack);
            stack.push(temp);
        }
    }


    // O(N)
    public static void printBackWord(StackUsingLinkedList<Integer> stack) {
        if (!stack.isEmpty()) {
            int data = stack.pop();
            System.out.print(data + " ");
            printBackWord(stack);
            stack.push(data);
        }
    }

}

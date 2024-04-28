package com.example.baraarj;

import java.util.Stack;

public class Problems {
    public static void main(String[] args) {

    }


    public String makeGood(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && Character.toLowerCase(c) == Character.toLowerCase(stack.peek())) {
                stack.pop(); // Remove the top character as it matches the current one in a case-insensitive comparison
            } else {
                stack.push(c); // Otherwise, push the current character onto the stack
            }
        }

        // Building the resulting string from the stack
        StringBuilder result = new StringBuilder();
        for (char c : stack) {
            result.append(c);
        }
        return result.toString();
    }
    }

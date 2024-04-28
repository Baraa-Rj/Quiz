package com.example.baraarj;

import static java.lang.System.out;

public class StackUsingCursorArray<T extends Comparable<T>> {
    private final CursorArray<T> cursorArray;
    private final int top;
    private int length;

    public StackUsingCursorArray(int length) {
        cursorArray = new CursorArray<>(length);
        top = cursorArray.createList();
        this.length = length;
    }

    public void push(T data) {
        int p = cursorArray.malloc();
        if (p == 0)
            out.println("Memory is Full");
        else {
            cursorArray.cursorArray[p] = new CNode<>(data, cursorArray.cursorArray[top].next);
            cursorArray.cursorArray[top].next = p;
        }
    }

    public T pop() {
        if (cursorArray.isEmpty(top)) {
            out.println("Stack is Empty");
            return null;
        } else {
            int p = cursorArray.cursorArray[top].next;
            T data = cursorArray.cursorArray[p].data;
            cursorArray.cursorArray[top].next = cursorArray.cursorArray[p].next;
            cursorArray.free(p);
            return data;
        }
    }

    public T peek() {
        if (cursorArray.isEmpty(top)) {
            out.println("Stack is Empty");
            return null;
        } else {
            int p = cursorArray.cursorArray[top].next;
            return cursorArray.cursorArray[p].data;
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isEmpty() {
        return cursorArray.isEmpty(top);

    }

    public void traverse() {
        cursorArray.traverse();
    }

    public void checkBalancedDelimiters(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(' || ch == '{' || ch == '[') {
                push((T) Character.valueOf(ch));
            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (isEmpty()) {
                    out.println("Not Balanced");
                    return;
                }
                char popped = (Character) pop();
                if ((ch == ')' && popped != '(') || (ch == '}' && popped != '{') || (ch == ']' && popped != '[')) {
                    out.println("Not Balanced");
                    return;
                }
            }
        }
        if (isEmpty()) {
            out.println("Balanced");
        } else {
            out.println("Not Balanced");
        }
    }

    public void checkBalancedDelimitersRec(String expression) {
        if (expression.length() == 0) {
            if (isEmpty()) {
                out.println("Balanced");
            } else {
                out.println("Not Balanced");
            }
            return;
        }
        char ch = expression.charAt(0);
        if (ch == '(' || ch == '{' || ch == '[') {
            push((T) Character.valueOf(ch));
        } else if (ch == ')' || ch == '}' || ch == ']') {
            if (isEmpty()) {
                out.println("Not Balanced");
                return;
            }
            char popped = (Character) pop();
            if ((ch == ')' && popped != '(') || (ch == '}' && popped != '{') || (ch == ']' && popped != '[')) {
                out.println("Not Balanced");
                return;
            }
        }
        checkBalancedDelimitersRec(expression.substring(1));
    }

    public double evaluateInfix(String expression) {
        StackUsingCursorArray<String> operators = new StackUsingCursorArray<>(expression.length());
        StackUsingCursorArray<Double> operands = new StackUsingCursorArray<>(expression.length());
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == ' ')
                continue;
            if (ch == '(') {
                operators.push(Character.toString(ch));
            } else if (Character.isDigit(ch)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                i--;
                operands.push(Double.parseDouble(sb.toString()));
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!operators.isEmpty() && precedence(Character.toString(ch)) <= precedence(operators.peek())) {
                    double val2 = operands.pop();
                    double val1 = operands.pop();
                    String op = operators.pop();
                    operands.push(applyOperation(val1, val2, op));
                }
                operators.push(Character.toString(ch));
            } else if (ch == ')') {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    double val2 = operands.pop();
                    double val1 = operands.pop();
                    String op = operators.pop();
                    operands.push(applyOperation(val1, val2, op));
                }
                operators.pop();
            }
        }
        while (!operators.isEmpty()) {
            double val2 = operands.pop();
            double val1 = operands.pop();
            String op = operators.pop();
            operands.push(applyOperation(val1, val2, op));
        }
        return operands.pop();
    }

    private int precedence(String string) {
        switch (string) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }

    private Double applyOperation(double val1, double val2, String op) {
        switch (op) {
            case "+":
                return val1 + val2;
            case "-":
                return val1 - val2;
            case "*":
                return val1 * val2;
            case "/":
                if (val2 == 0) {
                    out.println("Division by Zero");
                    return null;
                }
                return val1 / val2;
            default:
                return null;
        }
    }

    public String infixToPostfix(String expression) {
        StackUsingCursorArray<String> operators = new StackUsingCursorArray<>(expression.length());
        StringBuilder postfix = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == ' ')
                continue;
            if (ch == '(') {
                operators.push(Character.toString(ch));
            } else if (Character.isDigit(ch)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                i--;
                postfix.append(sb.toString()).append(" ");
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!operators.isEmpty() && precedence(Character.toString(ch)) <= precedence(operators.peek())) {
                    postfix.append(operators.pop()).append(" ");
                }
                operators.push(Character.toString(ch));
            } else if (ch == ')') {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    postfix.append(operators.pop()).append(" ");
                }
                operators.pop();
            }
        }
        while (!operators.isEmpty()) {
            postfix.append(operators.pop()).append(" ");
        }
        return postfix.toString();
    }

    public double evaluatePostFix(String expression) {
        StackUsingCursorArray<Double> operands = new StackUsingCursorArray<>(expression.length());
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == ' ')
                continue;
            if (Character.isDigit(ch)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                i--;
                operands.push(Double.parseDouble(sb.toString()));
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                double val2 = operands.pop();
                double val1 = operands.pop();
                operands.push(applyOperation(val1, val2, Character.toString(ch)));
            }
        }
        return operands.pop();
    }
    public T clear(){
        while (!isEmpty()){
            pop();
        }
        return null;
    }

    @Override
    public String toString() {
        return cursorArray.toString();
    }
}

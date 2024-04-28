package com.example.baraarj;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main extends Application {
    TextField textField = new TextField();
    TextField textField2 = new TextField();
    TextField textField3 = new TextField();
    TextField textField4 = new TextField();
    Label label = new Label();
    Label label2 = new Label();
    Label label3 = new Label();
    Label label4 = new Label();


    public static void main(String[] args) {
        launch(args);
    }

    public static String readFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                sb.append(line);
            }

            scanner.close();
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkBalancedDelimiters(String expression) {
        StackUsingCursorArray<Character> stack = new StackUsingCursorArray<>(expression.length());
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if ((ch == ')' && top != '(') || (ch == ']' && top != '[') || (ch == '}' && top != '{')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static String infixToPostfix(String expression) {
        StackUsingCursorArray<Character> stack = new StackUsingCursorArray<>(expression.length());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == ' ')
                continue;
            if (Character.isDigit(ch)) {
                sb.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    sb.append(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    sb.append(stack.pop());
                }
                stack.push(ch);
            }
        }
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    public static int precedence(char ch) {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }

    public static String infixToPrefix(String expression) {
        return reverse(infixToPostfix(reverse(expression)));
    }

    private static String reverse(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch == '(') {
                sb.append(')');
            } else if (ch == ')') {
                sb.append('(');
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        VBox vBox2 = new VBox();
        VBox vBox3 = new VBox();
        VBox vBox4 = new VBox();
        label.setText("Enter the expression");
        label2.setText("Infix to Postfix");

        label3.setText("Infix to Prefix");
        label4.setText("evaluate the expression");

        vBox.getChildren().addAll(label, textField);
        vBox2.getChildren().addAll(label2, textField2);
        vBox3.getChildren().addAll(label3, textField3);
        vBox4.getChildren().addAll(label4, textField4);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox2.setAlignment(javafx.geometry.Pos.CENTER);
        vBox3.setAlignment(javafx.geometry.Pos.CENTER);
        vBox4.setAlignment(javafx.geometry.Pos.CENTER);
        borderPane.setLeft(vBox);
        borderPane.setTop(vBox2);
        borderPane.setBottom(vBox3);
        borderPane.setRight(vBox4);
        textField.setOnAction(e -> {
            String expression = textField.getText();
            textField2.setText(infixToPostfix(expression));
            textField3.setText(infixToPrefix(expression));
            textField4.setText(evaluate(expression));

        });

        stage.setScene(new Scene(borderPane, 400, 400));
        stage.show();
    }

    public String evaluate(String expression) {
        StackUsingCursorArray<Integer> stack = new StackUsingCursorArray<>(expression.length());
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            stack.push(ch - '0');
                int x = stack.pop();
                int y = stack.pop();
                switch (ch) {
                    case '+' -> stack.push(y + x);
                    case '-' -> stack.push(y - x);
                    case '*' -> stack.push(y * x);
                    case '/' -> stack.push(y / x);
                    default -> stack.push(0);
                }
            }
        return String.valueOf(stack.pop());
    }

    public static <T extends Comparable<T>> Node<T> reverse(Node<T> head) {
        Node<T> current = head;
        Node<T> prev = null;
        while (current != null) {
            Node<T> next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }
        return prev;
    }

    public static <T extends Comparable<T>> Node<T> reverseRecursion(Node<T> head) {
        return reverseRecursion(head, null);
    }

    public static <T extends Comparable<T>> Node<T> reverseRecursion(Node<T> current, Node<T> prev) {
        if (current == null)
            return prev;
        else {
            Node<T> next = current.getNext();
            current.setNext(prev);
            return reverseRecursion(next, current);
        }

    }
 /*   public static <T extends Comparable<T>> Node<T> reverse(Node<T> head) {
        Node<T> current = head;
        Node<T> prev = null;
        while (current != null) {
            Node<T> next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }
        return prev;
    }

    public static <T extends Comparable<T>> Node<T> reverseRecursion(Node<T> head) {
        return reverseRecursion(head, null);
    }

    public static <T extends Comparable<T>> Node<T> reverseRecursion(Node<T> current, Node<T> prev) {
        if (current == null)
            return prev;
        else {
            Node<T> next = current.getNext();
            current.setNext(prev);
            return reverseRecursion(next, current);
        }*/
}


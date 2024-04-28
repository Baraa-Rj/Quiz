package com.example.baraarj;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Lab1 extends Application {
    GridPane gridPane;
    Label lbReverseString;
    Label lbBinarySearch;
    Label lbSumDigits;
    TextField tfReverseString;
    TextField tfBinarySearch;
    TextField tfSumDigits;
    Button btnReverseString;
    Button btnBinarySearch;
    Button btnSumDigits;

    TabPane tabPane;
    Tab tab;

    public static void main(String[] args) {
        launch(args);
    }

    public static void printNumbers(int number) {
        if (number >= 0) {
            System.out.print(number + " ");
        } else {
            return;
        }
        printNumbers(number - 1);


    }

    private static double power(int m, int n, int result) {

        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return result;
        } else {
            return power(m, --n, result * m);
        }
    }

    public static double power(int m, int n) {
        return power(m, n, 1);
    }

    private static int countParaphrases(String s, char target, int index) {
        if (!(index == s.length())) {
            if (!(s.charAt(index) == target)) {
                return countParaphrases(s, target, ++index);
            }
            return 1 + countParaphrases(s, target, ++index);
        }
        return 0;
    }

    public static int countParaphrases(String s, char target) {
        return countParaphrases(s, target, 0);
    }

    public static int sumDigits(int n, int result) {
        if (n == 0) {
            if (result < 10) {
                return result;
            } else {
                return sumDigits(result, 0);
            }
        }
        return sumDigits(n / 10, result + (n % 10));
    }

    private static int sumDigits(int n) {
        return sumDigits(n, 0);
    }

    private static int binarySearch(int[] array, int key, int left, int right) {
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (array[mid] > key) {
            return binarySearch(array, key, left, mid - 1);
        } else if (array[mid] < key) {
            return binarySearch(array, key, mid + 1, right);
        } else if (array[mid] == key) {
            return key;
        } else {
            return -1;
        }
    }

    public static int binarySearch(int[] array, int key) {
        return binarySearch(array, key, 0, array.length - 1);
    }

    public static String reverseString(String string) {
        if (string == null || string.length() <= 1 || !string.contains(" ")) {
            return string;
        }
        if (string.substring(string.indexOf(" ") + 1).isEmpty()) {
            return reverseString(string.substring(string.indexOf(" ") + 1)) + "" + string.substring(0, string.indexOf(" "));
        } else {
            return reverseString(string.substring(string.indexOf(" ") + 1)) + " " + string.substring(0, string.indexOf(" "));
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.show();
    }

    public void setGridPane() {
        gridPane = new GridPane();
        lbReverseString = new Label("Reverse String");
        lbBinarySearch = new Label("Binary Search");
        lbSumDigits = new Label("Sum Digits");
        tfReverseString = new TextField();
        tfBinarySearch = new TextField();
        tfSumDigits = new TextField();
        btnReverseString = new Button("Reverse String");
        btnBinarySearch = new Button("Binary Search");
        btnSumDigits = new Button("Sum Digits");
        gridPane.add(lbReverseString, 0, 0);
        gridPane.add(tfReverseString, 1, 0);
        gridPane.add(btnReverseString, 2, 0);
        gridPane.add(lbBinarySearch, 0, 1);
        gridPane.add(tfBinarySearch, 1, 1);
        gridPane.add(btnBinarySearch, 2, 1);
        gridPane.add(lbSumDigits, 0, 2);
        gridPane.add(tfSumDigits, 1, 2);
        gridPane.add(btnSumDigits, 2, 2);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        btnBinarySearch.setOnAction(e -> {
            int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            int key = Integer.parseInt(tfBinarySearch.getText());
            int result = binarySearch(array, key);
            if (result == -1) {
               tfBinarySearch.setText("Element not found");
            } else {
                tfBinarySearch.setText("Element found at index " + result);
            }
        });
        btnReverseString.setOnAction(e -> {

            tfReverseString.setText(reverseString(tfReverseString.getText()));
        });
        btnSumDigits.setOnAction(e -> {
            int number = Integer.parseInt(tfSumDigits.getText());
            tfSumDigits.setText(sumDigits(number)+" ");
        });

    }

    public Tab getTab() {
        tabPane = new TabPane();
        tab = new Tab();
        tab.setText("Lab1");
        setGridPane();
        tab.setContent(gridPane);
        return tab;
    }

    public static class LinkedList<T extends Comparable<T>> {
        private Node<T> head;

        public LinkedList() {
        }

        public void insert(T data) {
            Node<T> newNode = new Node<>(data);
            if (head == null) {
                head = newNode;
            } else {
                Node<T> current = head;
                Node<T> previous = null;
                for (; (current != null) && (current.getData().compareTo(data) < 0);
                     previous = current, current = current.getNext())
                    ;
                if (previous == null) {
                    newNode.setNext(head);
                    head = newNode;
                } else {
                    newNode.setNext(current);
                    previous.setNext(newNode);
                }
            }
        }

        public void delete(T data) {
            if (head == null) {
                return;
            } else {
                Node<T> current = head;
                Node<T> previous = null;
                for (; (current != null) && (current.getData().compareTo(data) != 0);
                     previous = current, current = current.getNext())
                    ;
                if (previous == null)
                    head = head.getNext();
                else if (current != null) {
                    previous.setNext(current.getNext());
                }
            }
        }

        private Node<T> find(T data, Node<T> node) {
            if (node == null || node.getData().compareTo(data) == 0) {
                return node;
            }
            return find(data, node.getNext());
        }

        public Node<T> find(T data) {
            return find(data, head);
        }

        public String traverse() {
            Node<T> current = head;
            System.out.print("Head ->");
            StringBuilder sb = new StringBuilder();
            sb.append("Head ->");
            while (current != null) {
                sb.append(current.getData()).append("->");
                System.out.print(current.getData() + "-> ");
                current = current.getNext();
            }
            System.out.print("null");
            sb.append("null");
            return sb.toString();
        }

        public Node<T> getHead() {
            return head;
        }

        public void setHead(Node<T> head) {
            this.head = head;
        }
    }
}

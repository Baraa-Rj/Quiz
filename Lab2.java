package com.example.baraarj;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Lab2<T extends Comparable<T>> extends Application {
    Tab tab;
    Label lbFind, lbDelete, lbInsert, lbResult;
    TextField tfFind, tfDelete, tfInsert;
    Button btExecute, btPrint, btReverse;
    GridPane gp;
    LinkedList<T> list = new LinkedList<>();


    public static <T extends Comparable<T>> Node<T> reverse(Node<T> head) {
        if (head == null) {
            return null;
        } else {
            Node<T> current = head;
            Node<T> previous = null;
            while (current != null) {
                Node<T> next = current.getNext();
                current.setNext(previous);
                previous = current;
                current = next;
            }
            return previous;
        }
    }

    public static <T extends Comparable<T>> Node<T> reverseRecursion(Node<T> head, Node<T> nextNode) {
        if (nextNode == null) {
            return head;
        } else {
            Node<T> newHead = reverseRecursion(nextNode, nextNode.getNext());
            nextNode.setNext(head);
            head.setNext(null);
            return newHead;
        }
    }

    public static <T extends Comparable<T>> void reverseRecursion(Node<T> head) {
        reverseRecursion(head, head.getNext());
    }

    @Override
    public void start(Stage stage) throws Exception {
        TabPane pane = new TabPane();
        pane.getTabs().add(getTab());
        stage.setScene(new Scene(pane, 500, 500));
        stage.show();
    }

    public Tab getTab() {
        lbDelete = new Label("Delete");
        lbFind = new Label("Find");
        lbInsert = new Label("Insert");
        tfDelete = new TextField();
        tfFind = new TextField();
        tfInsert = new TextField();
        btExecute = new Button("Execute");
        lbResult = new Label("Result");
        btExecute.setOnAction(e -> {
            if (!tfDelete.getText().isEmpty()) {
                list.delete((T) tfDelete.getText());
                lbResult.setText("Deleted " + tfDelete.getText());
                tfDelete.clear();
            }
            if (!tfFind.getText().isEmpty()) {
                lbResult.setText(list.find((T) tfFind.getText()).toString());
                tfFind.clear();
            }
            if (!tfInsert.getText().isEmpty()) {
                list.insert((T) tfInsert.getText());
                lbResult.setText("Inserted " + tfInsert.getText());
                tfInsert.clear();
            }
        });
        btPrint = new Button("Print");
        btPrint.setOnAction(e -> {
            lbResult.setText(list.traverse());
        });
        btReverse = new Button("Reverse");
        btReverse.setOnAction(e -> {
            list.setHead(reverse(list.getHead()));
            lbResult.setText(list.traverse());
        });
        gp = new GridPane();
        gp.add(lbFind, 0, 0);
        gp.add(lbDelete, 0, 1);
        gp.add(lbInsert, 0, 2);
        gp.add(tfFind, 1, 0);
        gp.add(tfDelete, 1, 1);
        gp.add(tfInsert, 1, 2);
        gp.add(btExecute, 0, 3);
        gp.add(btPrint, 1, 3);
        gp.add(lbResult, 0, 4);
        gp.add(btReverse, 2, 3);
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(10);
        tab = new Tab("lab2");
        tab.setContent(gp);
        return tab;
    }
}
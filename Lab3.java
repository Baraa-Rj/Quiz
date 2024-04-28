package com.example.baraarj;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Lab3 extends Application {
    private TextField tfInsert, tfDelete, tfFind;
    private Label lblInsert, lblDelete, lblFind, lbResult;
    private Button btnReverse, btnInsert, btnDelete, btnFind;
    private GridPane gp;
    private DLinkedList<Integer> list = new DLinkedList<>();
    private BorderPane bp;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    public Tab getTab() {
        lblDelete = new Label("Delete");
        lblFind = new Label("Find");
        lblInsert = new Label("Insert");
        lbResult = new Label("Result");
        tfDelete = new TextField();
        tfFind = new TextField();
        tfInsert = new TextField();
        btnDelete = new Button("Delete");
        btnDelete.setOnAction(e -> {
            list.delete(Integer.parseInt(tfDelete.getText()));
            lbResult.setText(list.toString());
            tfDelete.clear();
        });
        btnFind = new Button("Find");
        btnFind.setOnAction(e -> {
            lbResult.setText(list.find(Integer.parseInt(tfFind.getText())).toString());
            tfFind.clear();
        });

        btnInsert = new Button("Insert");
        btnInsert.setOnAction(e -> {
            list.insert(Integer.parseInt(tfInsert.getText()));
            lbResult.setText(list.toString());
            tfInsert.clear();
        });
        btnReverse = new Button("Reverse");
        btnReverse.setOnAction(e -> {
            list.setHead(list.reverseDoublyLinkedList(list.getHead()));
            lbResult.setText(list.toString());
        });
        gp = new GridPane();
        gp.add(lblInsert, 0, 0);
        gp.add(tfInsert, 1, 0);
        gp.add(btnInsert, 2, 0);
        gp.add(lblDelete, 0, 1);
        gp.add(tfDelete, 1, 1);
        gp.add(btnDelete, 2, 1);
        gp.add(lblFind, 0, 2);
        gp.add(tfFind, 1, 2);
        gp.add(btnFind, 2, 2);
        gp.add(btnReverse, 2, 3);
        bp = new BorderPane();
        bp.setCenter(gp);
        bp.setBottom(lbResult);
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setGridLinesVisible(false);
        gp.setStyle("-fx-background-color: Red");
        gp.setStyle("-fx-padding: 10");
        gp.setAlignment(javafx.geometry.Pos.CENTER);
        return new Tab("Lab3", bp);
    }
}
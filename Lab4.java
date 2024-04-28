package com.example.baraarj;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Lab4 extends Application {
    Tab tab;
    Label lbInsertForFirstList, lbInsertForSecondList, lbDelete, lbClear, lbResult;
    TextField tfInsert, tfDelete, tfClear, tfInsertForSecondList;
    Button btInsert, btDelete, btClear, btInsertSecond, btMerge;
    BorderPane bp;
    GridPane gp;
    private CursorArray<Integer> cursorArray = new CursorArray<>(30);

    @Override
    public void start(Stage stage) throws Exception {
        getTab();
        stage.setScene(new javafx.scene.Scene(bp, 500, 500));
        stage.show();
    }

    public Tab getTab() {
        int firstList = cursorArray.createList();
        int secondList = cursorArray.createList();
        lbInsertForFirstList = new Label("Insert");
        lbDelete = new Label("Delete");
        lbClear = new Label("Clear");
        lbResult = new Label();
        tfClear = new TextField();
        tfDelete = new TextField();
        tfInsert = new TextField();
        btClear = new Button("Clear");
        btDelete = new Button("Delete");
        btInsert = new Button("Insert");
        lbInsertForSecondList = new Label("Insert for second list");
        tfInsertForSecondList = new TextField();
        btInsertSecond = new Button("Insert");
        btMerge = new Button("Merge");
        btMerge.setOnAction(e -> {
            cursorArray.mergeSortedLists(firstList, secondList);
            lbResult.setText("Merged" + cursorArray.toString());
        });
        btInsertSecond.setOnAction(e -> {
            int l = Integer.parseInt(tfInsertForSecondList.getText());
            cursorArray.insertSorted(l, secondList);
            lbResult.setText("Inserted" + cursorArray.toString());
        });
        btClear.setOnAction(e -> {
            tfInsert.clear();
            tfDelete.clear();
            tfClear.clear();
            lbResult.setText("");
        });
        btInsert.setOnAction(e -> {
            int l = Integer.parseInt(tfInsert.getText());
            cursorArray.insertSorted(l, firstList);
            lbResult.setText("Inserted" + cursorArray.toString());
        });


        btDelete.setOnAction(e -> {
            int l = Integer.parseInt(tfDelete.getText());
            cursorArray.free(l);
            lbResult.setText("Deleted" + cursorArray.toString());

        });
        gp = new GridPane();
        gp.add(lbInsertForFirstList, 0, 0);
        gp.add(tfInsert, 1, 0);
        gp.add(btInsert, 2, 0);

        gp.add(lbInsertForSecondList, 0, 1);
        gp.add(tfInsertForSecondList, 1, 1);
        gp.add(btInsertSecond, 2, 1);

        gp.add(lbDelete, 0, 2);
        gp.add(tfDelete, 1, 2);
        gp.add(btDelete, 2, 2);
        gp.add(btClear, 2, 3);
        gp.add(btMerge, 2, 4);
        gp.setAlignment(javafx.geometry.Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(10);

        bp = new BorderPane();
        bp.setCenter(gp);
        tab = new Tab("Lab4");
        bp.setBottom(lbResult);
        tab.setContent(bp);
        return tab;
    }
}

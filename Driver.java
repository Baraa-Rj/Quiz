package com.example.baraarj;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Driver extends Application {
    Tab tabLab0, tabLab1, tabLab2, tabLab3, tabLab4, tabLab6;
    TabPane tabPane;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Labs");
        TestName testName = new TestName();
        tabLab0 = testName.getTab();
        Lab1 lab1 = new Lab1();
        tabLab1 = lab1.getTab();
        Lab2 lab2 = new Lab2();
        Lab3 lab3 = new Lab3();
        Lab4 lab4 = new Lab4();
        Lab6 lab6 = new Lab6();
        tabLab6 = lab6.getTab();
        tabLab3 = lab3.getTab();
        tabLab2 = lab2.getTab();
        tabLab4 = lab4.getTab();
        tabPane = new TabPane();
        tabPane.getTabs().addAll(tabLab0, tabLab1, tabLab2, tabLab3, tabLab4, tabLab6);
        stage.setScene(new Scene(tabPane, 500, 500));
        stage.show();
    }
}
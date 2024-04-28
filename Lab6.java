package com.example.baraarj;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class Lab6 {
    private QueueUsingStack<Integer> queue = new QueueUsingStack<>();

    private int min = 0;
    private int totalIn = 0;
    private int totalOut = 0;
    private int totalWaitingTime = 0;
    private Label lbMessage = new Label();
    private TextField tfTime = new TextField();
    private TextField tfTotalCustomers = new TextField();
    private TextField tfTotalInQueue = new TextField();
    private TextField tfAverageWaitingTime = new TextField();
    private GridPane gridPane = new GridPane();
    private Button btnSimulate = new Button("Simulate");
    private Tab tab = new Tab("Cashier");
    private Button btnClear = new Button("Clear");


    public Tab getTab() {
        lbMessage.setText("Enter the time to simulate");
        gridPane.add(lbMessage, 0, 0);
        gridPane.add(tfTime, 1, 0);
        gridPane.add(btnSimulate, 0, 1);
        gridPane.add(btnClear, 1, 1);
        gridPane.add(new Label("Total customers processed: "), 0, 2);
        gridPane.add(tfTotalCustomers, 1, 2);
        gridPane.add(new Label("Total customers in queue: "), 0, 3);
        gridPane.add(tfTotalInQueue, 1, 3);
        gridPane.add(new Label("Average waiting time: "), 0, 4);
        gridPane.add(tfAverageWaitingTime, 1, 4);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        tab.setContent(gridPane);
        btnSimulate.setOnAction(e -> {
            simulate(Integer.parseInt(tfTime.getText()));
            tfTotalCustomers.setText(String.valueOf(totalOut));
            tfTotalInQueue.setText(String.valueOf(totalIn));
            double averageWaitingTime = (double) totalWaitingTime / totalOut;
            tfAverageWaitingTime.setText(String.valueOf(averageWaitingTime));
        });
        btnClear.setOnAction(e -> {
            tfTime.clear();
            tfTotalCustomers.clear();
            tfTotalInQueue.clear();
            tfAverageWaitingTime.clear();
        });
        return tab;
    }

    public int addCustomer() {
        Random random = new Random();
        int newCustomers = random.nextInt(4);
        if (newCustomers == 0 || newCustomers == 3) {
            return 0;
        } else if (newCustomers == 2) {
            queue.enqueue(min + 1);
            queue.enqueue(min + 1);
            totalIn += 2;
            return 2;
        } else {
            queue.enqueue(min + 1);
            totalIn++;
            return 1;
        }
    }

    public int processCustomer() {
        int pr = 0;
        if (!queue.isEmpty()) {
            int processTime = queue.dequeue().getData();
            if (min >= processTime) {
                int waitingTime = min - processTime + 1;
                totalWaitingTime += waitingTime;
                totalOut++;
                totalIn--;
                pr = 1;
            }
        }
        return pr;
    }

    public void simulate(int time) {
        for (int i = 0; i < time; i++) {
            min = i;
            int in = addCustomer();
            int out = processCustomer();
            totalIn += in;
            totalOut += out;
        }
        while (!queue.isEmpty()) {
            min++;
            processCustomer();
        }
        double averageWaitingTime = (double) totalWaitingTime / totalOut;
        System.out.println("Total customers processed: " + totalOut);
        System.out.println("Total customers in queue: " + totalIn);
        System.out.println("Average waiting time: " + averageWaitingTime);

    }

    public static void main(String[] args) {
        Lab6 cashier = new Lab6();
        cashier.simulate(100);

    }
}
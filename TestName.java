package com.example.baraarj;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TestName extends Application {
    static MyList<Name> names = new MyList<>();
    TabPane tabPane;
    Tab tabLab0;
    Tab lab0;
    BorderPane borderPane;
    GridPane gridPane;
    TextField tfAdd;
    TextField tfFind;
    TextField tfDelete;
    TextField tfTotalNames;
    Button btnAdd;
    Button btnDelete;
    Button btFind;
    Button btTotalNames;
    Button btLoad;
    SplitPane splitPane;
    Label lbMax;
    Label lbResult;
    Label lbMaxFM;
    VBox vBoxFM;
    VBox vBoxTotalSplit;
    Scene scene1;
    RadioButton rbMale;
    RadioButton rbFemale;
    ToggleGroup toggleGroup;
    HBox hBoxMax;
    ComboBox<String> comboBoxColors;
    TableView<Name> tableView;
    TableColumn<Name, String> name;
    TableColumn<Name, Integer> frequency;
    TableColumn<Name, Character> gender;
    MenuBar menuBar;
    Menu menu;
    MenuItem save, close;
    ObservableList<Name> products = FXCollections.observableArrayList();
    File file = new File("usa.txt");


    public boolean readFile() {
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    char gender = parts[1].charAt(0);
                    int freq = Integer.parseInt(parts[2]);
                    names.add(new Name(parts[0], freq, gender));
                }
                return true;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        tabPane = new TabPane();
        lab0 = new Tab();

        stage.show();
    }

    public Tab getTab() {
        borderPane = new BorderPane();
        gridPane = new GridPane();
        btLoad = new Button("Load");
        btLoad.setPrefWidth(50);
        btLoad.setOnAction(e -> {
            if (readFile()) {
                System.out.println("File Read!");
            } else {
                System.out.println("Error");
            }
        });
        btnAdd = new Button("Add");
        lbResult = new Label();
        btnAdd.setPrefWidth(50);
        tfAdd = new TextField();
        btnAdd.setOnAction(e -> {
            String line = tfAdd.getText();
            String[] values = line.split(" ");
            names.add(new Name(values[0], Integer.parseInt(values[1]), values[2].charAt(0)));
            products.add(new Name(values[0], Integer.parseInt(values[1]), values[2].charAt(0)));
            lbResult.setText("Added Successfully");
            tfAdd.clear();
        });
        tfAdd.setPromptText("Information");
        btFind = new Button("Find");
        btFind.setPrefWidth(50);
        tfFind = new TextField();
        btFind.setOnAction(e -> {
            for (int i = 0; i < names.size(); i++) {
                if (names.get(i).getName().compareTo(tfFind.getText()) == 0) {
                    lbResult.setText("Name has been found");

                }
            }

        });
        btnDelete = new Button("Delete");
        btnDelete.setPrefWidth(50);
        btnDelete.setOnAction(e -> {
            for (int i = 0; i < names.size(); i++) {
                if (names.get(i).getName().compareTo(tfDelete.getText()) == 0) {
                    names.delete(names.get(i));
                    lbResult.setText("the name has been deleted");
                }
            }
        });
        tfDelete = new TextField();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(btLoad, 1, 0);
        gridPane.add(btnAdd, 0, 1);
        gridPane.add(tfAdd, 1, 1);
        gridPane.add(btnDelete, 0, 2);
        gridPane.add(tfDelete, 1, 2);
        gridPane.add(btFind, 0, 3);
        gridPane.add(tfFind, 1, 3);
        gridPane.add(lbResult, 0, 4);
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);
        menuBar = new MenuBar();
        menu = new Menu("File");
        save = new MenuItem("Save");
        save.setOnAction(e -> {
            writeOnFile();
            System.out.println("Saved successfully!");
        });
        close = new MenuItem("Close");
        close.setOnAction(e -> {
            System.exit(0);
        });
        menu.getItems().addAll(save, close);
        menuBar.getMenus().add(menu);
        borderPane.setTop(menuBar);
        splitPane = new SplitPane(borderPane, initializeSplitPane());
        tabLab0 = new Tab("Lab0");
        tabLab0.setContent(splitPane);
        return tabLab0;
    }

    public VBox initializeSplitPane() {
        btTotalNames = new Button("Total Name");
        tfTotalNames = new TextField();
        lbMax = new Label();
        btTotalNames.setOnAction(e -> {
            for (int i = 0; i < names.size(); i++) {
                if (names.get(i).getName().equals(tfTotalNames.getText().trim())) {
                    lbMax.setText(" " + names.get(i).getFrequency());
                }
            }
        });
        hBoxMax = new HBox(10);
        hBoxMax.getChildren().addAll(btTotalNames, tfTotalNames, lbMax);
        toggleGroup = new ToggleGroup();

        rbFemale = new RadioButton("Female");
        rbMale = new RadioButton("Male");

        rbMale.setToggleGroup(toggleGroup);
        rbFemale.setToggleGroup(toggleGroup);
        lbMaxFM = new Label();
        rbMale.setOnAction(e -> {
            int max = 0;
            for (int i = 0; i < names.size(); i++) {

                if (names.get(i).getFrequency() > max && names.get(i).getGender() == 'M') {
                    max = names.get(i).getFrequency();
                }
            }
            lbMaxFM.setText(max + " ");
        });
        rbFemale.setOnAction(e -> {
            int max = 0;
            for (int i = 0; i < names.size(); i++) {

                if (names.get(i).getFrequency() > max && names.get(i).getGender() == 'F') {
                    max = names.get(i).getFrequency();
                }
            }
            lbMaxFM.setText(max + " ");
        });
        comboBoxColors = new ComboBox<>();
        comboBoxColors.setValue("Default");
        comboBoxColors.getItems().addAll("Yellow", "Green", "Blue", "Red");
        comboBoxColors.setOnAction(e -> {
            String color = comboBoxColors.getValue();
            switch (color) {
                case "Yellow":
                    splitPane.setStyle("-fx-background-color: Yellow");
                    break;
                case "Green":
                    splitPane.setStyle("-fx-background-color: Green");
                    break;
                case "Blue":
                    splitPane.setStyle("-fx-background-color: Blue");
                    break;
                case "Red":
                    splitPane.setStyle("-fx-background-color: Red");
                    break;
                default:
                    break;
            }
        });
        tableView = new TableView<>();
        name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        frequency = new TableColumn<>("Frequency");
        frequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        gender = new TableColumn<>("Gender");
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tableView.getColumns().add(name);
        tableView.getColumns().add(frequency);
        tableView.getColumns().add(gender);
        tableView.setItems(getProducts());
        vBoxFM = new VBox(10);
        vBoxFM.getChildren().addAll(rbFemale, rbMale, lbMaxFM);

        vBoxTotalSplit = new VBox(10);
        vBoxTotalSplit.setPadding(new Insets(10, 10, 10, 10));
        vBoxTotalSplit.getChildren().addAll(hBoxMax, vBoxFM, comboBoxColors, tableView);
        return vBoxTotalSplit;
    }

    public ObservableList<Name> getProducts() {
        products.add(new Name("Rjoub", 123, 'M'));
        products.add(new Name("Nawahdah", 23, 'M'));
        products.add(new Name("Zeer", 12, 'F'));
        return products;
    }

    public void writeOnFile() {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (int i = 0; i < names.size(); i++) {
                printWriter.write(names.get(i).toString());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

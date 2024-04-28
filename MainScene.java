package com.example.baraarj;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static java.lang.System.getProperty;
import static java.lang.System.out;

public class MainScene extends Application {
    private MenuItem menuOpen;
    private MenuItem menuExit;
    private MenuBar menuBar;
    private BorderPane borderPane = new BorderPane();
    private Button btLocation, btDistrict, btViewList;
    private VBox vBox;
    private Stage stage = new Stage();
    private File file;
    private DLinkedList<District> districts = new DLinkedList<>();

    @Override
    public void start(Stage stage) throws Exception {
        initializeMenu();
        initializeButtons();
        this.stage = stage;
        stage.setTitle("Martyrs");
        stage.setScene(new Scene(borderPane, 300, 200));
        stage.show();
    }

    private void initializeMenu() {
        menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem menuSave = new MenuItem("Save");
        menuOpen = new MenuItem("Open");
        menuOpen.setOnAction(e -> readFileAndSaveItInLinkedList());
        menuExit = new MenuItem("Exit");
        menuExit.setOnAction(e -> stage.close());
        fileMenu.getItems().addAll(menuSave, menuOpen, menuExit);
        menuBar.getMenus().addAll(fileMenu);
        borderPane.setTop(menuBar);
    }

    private void initializeButtons() {
        vBox = new VBox(10);
        btLocation = new Button("Location");
        btLocation.setPrefWidth(100);
        btDistrict = new Button("District");
        btDistrict.setPrefWidth(100);
        btViewList = new Button("View List");
        btViewList.setPrefWidth(100);
        btViewList.setOnAction(this::viewList);
        btDistrict.setOnAction(e -> switchToDistrictScene());
        btLocation.setOnAction(e -> switchToLocationScene());
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.getChildren().addAll(btLocation, btDistrict, btViewList);
        borderPane.setCenter(vBox);
    }


    private void switchToDistrictScene() {
        DistrictScene districtScene = new DistrictScene(districts);
        stage.setScene(districtScene.getScene());
    }

    public void viewList(ActionEvent event) {
        districts.traverse();
        districts.getHead().getNext().getData().getLocationList().traverse();
        districts.getHead().getNext().getData().getLocationList().getHead().getNext().getData().getMartyrsList().traverse();
    }

    private void switchToLocationScene() {
        LocationScene locationScene = new LocationScene(districts);
        stage.setScene(locationScene.getScene());
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public Stage getStage() {
        return stage;
    }

    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"), new FileChooser.ExtensionFilter("All Files", "*.*"));
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(getProperty("user.home")));
        file = fileChooser.showOpenDialog(stage);
    }

    private void readFileAndSaveItInLinkedList() {
        openFile();
        if (file != null) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] data = line.split(",");
                    if (data.length == 6 && isInteger(data[2])) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                        LocalDate date = LocalDate.parse(data[1], formatter);
                        Martyr martyr = new Martyr(data[0], date, Integer.parseInt(data[2]), data[3], data[4], data[5]);
                        District district = new District(data[4]);
                        Location location = new Location(data[3]);
                        DNode<District> districtNode = districts.find(district);
                        if (districtNode == null) {
                            districts.insert(district);
                            districtNode = districts.find(district);
                        }
                        if (districtNode.getData().getLocationList().find(location) == null) {
                            districtNode.getData().getLocationList().insert(location);
                        }
                        location.getMartyrsList().insert(martyr);
                    } else {
                        out.println("Invalid data on line: " + line);
                    }
                }
            } catch (FileNotFoundException e) {
                out.println("File not found: " + e.getMessage());
            } catch (Exception e) {
                out.println("Error processing the file: " + e.getMessage());
            }
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public DLinkedList<District> getDistricts() {
        return districts;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

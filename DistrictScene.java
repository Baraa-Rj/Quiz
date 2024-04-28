package com.example.baraarj;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Optional;

import static java.lang.System.out;

public class DistrictScene {
    private Stage stage = new Stage();
    private Scene sceneMain;
    private BorderPane borderPaneMain,borderPane;
    private VBox vBoxFirst,vBox;
    private HBox hBoxNavigate;
    private DatePicker datePicker;
    private Button btView, btInsert, btDelete, btUpdate, btSearch, btBack, btNavigate, btShowMartyrNumber, btLoadLocation, btNext, btPrevious, btShowAll;
    private Label lbAverageAges, lbNumberOfFemales, lbNumberOfMales, lbMaximumDate,lbDistrictName;
    private DLinkedList<District> districtList;
    public DistrictScene(DLinkedList<District> districts) {
        this.districtList = districts;
        initializePrimaryScene();
    }

    private void initializePrimaryScene() {
        borderPaneMain = new BorderPane();
        vBoxFirst = new VBox(10);
        vBoxFirst.setAlignment(Pos.CENTER);
        borderPaneMain.setPadding(new Insets(5));

        initializeButtons();
        vBoxFirst.getChildren().addAll(btInsert, btDelete, btUpdate, btSearch, btBack, btNavigate, btShowMartyrNumber, btLoadLocation, btShowAll);
        Label topLabel = new Label("Martyr Database");
        borderPaneMain.setTop(topLabel);
        borderPaneMain.setCenter(vBoxFirst);
        borderPaneMain.setBottom(new Label("Baraa Rjoub"));

        sceneMain = new Scene(borderPaneMain, 400, 400);
        stage.setScene(sceneMain);
    }

    private void initializeButtons() {
        btInsert = createButton("Insert", this::handleInsert);
        btNext = createButton("Next", this::handleNext);
        btPrevious = createButton("Previous", this::handlePrevious);
        btDelete = createButton("Delete", this::handleDelete);
        btUpdate = createButton("Update", this::handleUpdate);
        btSearch = createButton("Search", this::handleSearch);
        btBack = createButton("Back", this::handleBack);
        btNavigate = createButton("Navigate", this::handleNavigate);
        btShowMartyrNumber = createButton("Show Martyr Number", this::handleShowMartyrNumberByGivenDate);
        btLoadLocation = createButton("Load Location", this::handleLoadLocation);
        btShowAll = createButton("Show All", this::handleShowAll);
    }

    private void handleShowAll(ActionEvent actionEvent) {
        DNode<District> current = districtList.getHead().getNext();
        while (current != null) {
            Node<Location> locationCurrent = current.getData().getLocationList().getHead().getNext();
            while (locationCurrent != null) {
                out.println(locationCurrent.getData().toString());
                locationCurrent = locationCurrent.getNext();
            }
            out.println(current.getData().toString());
            current = current.getNext();
        }
    }

    private Button createButton(String text, EventHandler<ActionEvent> actionEvent) {
        Button button = new Button(text);
        button.setOnAction(actionEvent);
        button.setPrefWidth(250);
        return button;
    }


    private void handleInsert(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Insert District");
        dialog.setHeaderText("Insert a new district");
        dialog.setContentText("Please enter the district name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (districtList.find(new District(name)) != null) {
                showAlert(Alert.AlertType.ERROR, "Insertion Failed", "District already exists.");
                return;
            }
            districtList.insert(new District(name));
            showAlert(Alert.AlertType.INFORMATION, "Insertion Successful", "District added successfully.");
        });
    }

    private void handleDelete(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete District");
        dialog.setHeaderText("Delete a district");
        dialog.setContentText("Please enter the district name to delete:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            DNode<District> deleted = districtList.delete(new District(name));
            if (deleted != null) {
                showAlert(Alert.AlertType.INFORMATION, "Deletion Successful", "District deleted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Deletion Failed", "District not found.");
            }
        });
    }

    private void handleUpdate(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update District");
        dialog.setHeaderText("Update a district");
        dialog.setContentText("Please enter the district name to update:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            DNode<District> found = districtList.find(new District(name));
            if (found != null) {
                TextInputDialog updateDialog = new TextInputDialog();
                updateDialog.setTitle("Update District");
                updateDialog.setHeaderText("Update a district");
                updateDialog.setContentText("Please enter the new district name:");

                Optional<String> updateResult = updateDialog.showAndWait();
                updateResult.ifPresent(newName -> {
                    districtList.delete(found.getData());
                    districtList.insert(new District(newName));
                    showAlert(Alert.AlertType.INFORMATION, "Update Successful", "District updated successfully you need to add new locations now.");
                });
            } else {
                showAlert(Alert.AlertType.ERROR, "Update Failed", "District not found.");
            }
        });
    }

    private void handleSearch(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search District");
        dialog.setHeaderText("Search for a district");
        dialog.setContentText("Please enter the district name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            DNode<District> found = districtList.find(new District(name));
            if (found != null) {
                showAlert(Alert.AlertType.INFORMATION, "Search Successful", "District found: " + found.getData().toString() + "\n" + found.getData().getLocationList().toString());
            } else {
                showAlert(Alert.AlertType.ERROR, "Search Failed", "District not found.");
            }
        });
    }

    private void handleBack(ActionEvent event) {
        stage.close();
    }

    private void handleNavigate(ActionEvent event) {
        try {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Navigate");
            dialog.setHeaderText("Navigate to a district");
            hBoxNavigate = new HBox(10);
            hBoxNavigate.setAlignment(Pos.CENTER);
             borderPane = new BorderPane();
             lbDistrictName = new Label("District: " + districtList.getCurrent().getNext().getData().getDistrict());
             lbNumberOfMales = new Label("Males: " + districtList.getCurrent().getNext().getData().getTotalMale());
            lbNumberOfFemales = new Label("Females: " + districtList.getCurrent().getNext().getData().getTotalFemale());
            lbAverageAges = new Label("Average Age: " + districtList.getCurrent().getNext().getData().getAverageAge());
             lbMaximumDate = new Label("Max Date: " + districtList.getCurrent().getNext().getData().mostFrequncedDate());
             vBox = new VBox(10, lbDistrictName, lbNumberOfMales, lbNumberOfFemales, lbAverageAges, lbMaximumDate);
            hBoxNavigate.getChildren().addAll(btPrevious, btNext);
            borderPane.setCenter(vBox);
            borderPane.setBottom(hBoxNavigate);
            dialog.getDialogPane().setContent(borderPane);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.showAndWait();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "No districts found.");
        }
    }

    private void handlePrevious(ActionEvent actionEvent) {
        districtList.toPrevNode();
    }

    private void handleNext(ActionEvent actionEvent) {
        districtList.toNextNode();
    }

    private void handleShowMartyrNumberByGivenDate(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Show Martyr Number");
        alert.setHeaderText("Pick a date to show the number of martyrs:");

        datePicker = new DatePicker();
        VBox vBox = new VBox(10, datePicker);
        alert.getDialogPane().setContent(vBox);

        alert.showAndWait();

        LocalDate date = datePicker.getValue();
        if (date != null) {
            int count = getNumberOfDates(date);
            showAlert(Alert.AlertType.INFORMATION, "Martyr Number", "The number of martyrs on " + date + " is " + count);
        }
    }

    private int getNumberOfDates(LocalDate date) {
        int count = 0;
        DNode<District> current = districtList.getHead().getNext();
        while (current != null) {
            Node<Location> locationCurrent = current.getData().getLocationList().getHead().getNext();
            while (locationCurrent != null) {
                if (locationCurrent.getData().getMartyrsList().getHead().getNext().getData().getDateOfDeath().equals(date)) {
                    count++;
                }
                locationCurrent = locationCurrent.getNext();
            }
            current = current.getNext();
        }
        return count;
    }

    private void handleLoadLocation(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load Location");
        dialog.setHeaderText("Load location");
        dialog.setContentText("Please enter the location name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            boolean found = false;
            DNode<District> current = districtList.getHead();
            while (current != null) {
                if (current.getData().getLocationList().find(new Location(name)) != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Location Found", "Location found in " + current.getData().getDistrict());
                    found = true;
                    break;
                }
                current = current.getNext();
            }
            if (!found) {
                showAlert(Alert.AlertType.INFORMATION, "Location Not Found", "The location " + name + " was not found.");
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Scene getScene() {
        return sceneMain;
    }
}

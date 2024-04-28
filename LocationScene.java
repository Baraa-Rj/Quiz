package com.example.baraarj;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Optional;

public class LocationScene {
    private BorderPane bpPrimary;
    private Button btInsert, btUpdate, btDelete, btNavigate, btSearch, btInsertMartyr, btUpdateMartyr, btDeleteMartyr, btSearchMartyr, btView, btShowMartyrControl, btNext, btPrevious;
    private VBox vbPrimary;
    private Scene scene;
    private DLinkedList<District> district;
    private HBox hBoxNavigate;


    public LocationScene(DLinkedList<District> district) {
        this.district = district;
        firstScene();
    }

    private void firstScene() {
        initializeButton();
        vbPrimary = new VBox(10);
        vbPrimary.setAlignment(javafx.geometry.Pos.CENTER);
        vbPrimary.getChildren().addAll(btView, btInsert, btUpdate, btDelete, btNavigate, btSearch, btShowMartyrControl);
        bpPrimary = new BorderPane();
        bpPrimary.setCenter(vbPrimary);
        scene = new Scene(bpPrimary, 500, 500);
    }


    private Button createButton(String text, EventHandler<ActionEvent> actionEvent) {
        Button button = new Button(text);
        button.setOnAction(actionEvent);
        button.setPrefWidth(250);
        return button;
    }

    public void initializeButton() {
        btInsert = createButton("Insert", this::InsertSceneLocationWithinDistrict);
        btUpdate = createButton("Update", this::updateScene);
        btDelete = createButton("Delete", this::deleteScene);
        btNavigate = createButton("Navigate", this::navigateScene);
        btSearch = createButton("Search", this::searchScene);
        btInsertMartyr = createButton("Insert Martyr", this::InsertMartyrScene);
        btUpdateMartyr = createButton("Update Martyr", this::updateMartyrScene);
        btDeleteMartyr = createButton("Delete Martyr", this::deleteMartyrScene);
        btSearchMartyr = createButton("Search Martyr", this::searchMartyr);
        btView = createButton("View", this::printLocation);
        btShowMartyrControl = createButton("Show", this::showMartyrControl);
        btNext = createButton("Next", this::nextNode);
        btPrevious = createButton("Previous", this::previousNode);
    }

    private void previousNode(ActionEvent actionEvent) {
        Node<Location> node = district.getHead().getNext().getData().getLocationList().getHead().getNext();
        if (node != null) {
            node = node.getNext();
        } else {
            node = district.getHead().getNext().getData().getLocationList().getHead().getNext();
        }
    }

    private void nextNode(ActionEvent actionEvent) {
        Node<Location> node = district.getHead().getNext().getData().getLocationList().getHead().getNext();
        if (node != null) {
            node = node.getNext();
        } else {
            node = district.getHead().getNext().getData().getLocationList().getHead().getNext();
        }
    }

    private void showMartyrControl(ActionEvent actionEvent) {
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(btInsertMartyr, btUpdateMartyr, btDeleteMartyr, btSearchMartyr);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 500, 500);
        MainScene mainScene = new MainScene();
        mainScene.getStage().setScene(scene);
        mainScene.getStage().show();
    }

    private void printLocation(ActionEvent actionEvent) {
        DNode<District> targetDistrict = district.getHead().getNext();
        while (targetDistrict != null) {
            Node<Location> locationNode = targetDistrict.getData().getLocationList().getHead().getNext();
            while (locationNode != null) {
                System.out.println(locationNode.getData().getLocation());
                locationNode = locationNode.getNext();
            }
            targetDistrict = targetDistrict.getNext();
        }
    }


    private void InsertSceneLocationWithinDistrict(ActionEvent event) {
        TextInputDialog districtDialog = new TextInputDialog();
        districtDialog.setTitle("Insert Location in District");
        districtDialog.setHeaderText("Enter the district name");
        districtDialog.setContentText("District:");
        districtDialog.showAndWait().ifPresent(districtName -> {
            DNode<District> targetDistrict = district.find(new District(districtName));
            if (targetDistrict == null) {
                targetDistrict = new DNode<>(new District(districtName));
                district.insert(targetDistrict.getData());
                showAlert(Alert.AlertType.INFORMATION, "District Inserted", "District " + districtName + " has been inserted.");
            }
            while (true) {
                TextInputDialog locationDialog = new TextInputDialog();
                locationDialog.setTitle("Location Insertion");
                locationDialog.setHeaderText("Insert Location");
                locationDialog.setContentText("Enter the location name to be added to the " + districtName + " district (Cancel to finish):");
                Optional<String> result = locationDialog.showAndWait();
                if (result.isPresent() && !result.get().trim().isEmpty()) {
                    String locationName = result.get();
                    targetDistrict.getData().getLocationList().insert(new Location(locationName + " "));
                    showAlert(Alert.AlertType.INFORMATION, "Location Inserted", "Location " + locationName + " has been inserted in the " + districtName + " district.");
                } else {
                    break;
                }
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void updateScene(ActionEvent e) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Location");
        dialog.setHeaderText("Enter the district to update the location within");
        dialog.setContentText("District:");
        dialog.showAndWait().ifPresent(districtName -> {
            if (district.find(new District(districtName)) == null) {
                showAlert(Alert.AlertType.ERROR, "District Not Found", "District " + districtName + " not found.");
                return;
            }
            TextInputDialog oldLocationDialog = new TextInputDialog();
            oldLocationDialog.setTitle("Update Location");
            oldLocationDialog.setHeaderText("Enter the location to update");
            oldLocationDialog.setContentText("Location:");
            oldLocationDialog.showAndWait().ifPresent(oldLocation -> {
                DNode<District> targetDistrict = district.find(new District(districtName));
                Node<Location> targetLocation = targetDistrict.getData().getLocationList().find(new Location(oldLocation));
                if (targetLocation != null) {
                    TextInputDialog newLocationDialog = new TextInputDialog();
                    newLocationDialog.setTitle("Update Location");
                    newLocationDialog.setHeaderText("Enter the new location name");
                    newLocationDialog.setContentText("Location:");
                    newLocationDialog.showAndWait().ifPresent(newLocation -> {
                        targetDistrict.getData().getLocationList().delete(targetLocation.getData());
                        targetDistrict.getData().getLocationList().insert(new Location(newLocation));
                        showAlert(Alert.AlertType.INFORMATION, "Location Updated", "Location " + oldLocation + " has been updated to " + newLocation + " in the " + districtName + " district.");
                    });
                } else {
                    showAlert(Alert.AlertType.ERROR, "Location Not Found", "Location " + oldLocation + " not found.");
                }
            });
        });
    }

    private void deleteScene(ActionEvent e) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Location");
        dialog.setHeaderText("Enter the location to delete");
        dialog.setContentText("Location:");
        dialog.showAndWait().ifPresent(location -> {
            DNode<District> targetDistrict = district.getHead().getNext();
            while (targetDistrict != null) {
                Node<Location> locationNode = targetDistrict.getNext().getData().getLocationList().getHead();
                while (locationNode != null) {
                    if (locationNode.getData().getLocation().compareTo(location) == 0) {
                        targetDistrict.getData().getLocationList().delete(locationNode.getData());
                        showAlert(Alert.AlertType.INFORMATION, "Location Deleted", "Location " + location + " has been deleted.");
                        break;
                    }
                    locationNode = locationNode.getNext();
                }
                targetDistrict = targetDistrict.getNext();
            }
            if (targetDistrict == null) {
                showAlert(Alert.AlertType.ERROR, "Location Not Found", "Location " + location + " not found.");
            }
        });
    }

    private void navigateScene(ActionEvent e) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Navigate");
        dialog.setHeaderText("Navigate to a district");
        hBoxNavigate = new HBox(10);
        hBoxNavigate.setAlignment(Pos.CENTER);
        BorderPane borderPane = new BorderPane();
        Label lbLocation = new Label("Location: ");
        Label lbTotalMales = new Label("Males: ");
        Label lbTotalFemales = new Label("Females: ");
        Label lbAverageAge = new Label("Average Age: ");
        Label lbYoungest = new Label("Youngest Date: ");
        Label lbOldest = new Label("Oldest Date: ");
        VBox vBox = new VBox(10, lbLocation, lbTotalMales, lbTotalFemales, lbAverageAge, lbYoungest, lbOldest);
        hBoxNavigate.getChildren().addAll(btPrevious, btNext);
        borderPane.setCenter(vBox);
        borderPane.setBottom(hBoxNavigate);
        dialog.getDialogPane().setContent(borderPane);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }


    private void searchScene(ActionEvent e) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Location");
        dialog.setHeaderText("Enter the location to search");
        dialog.setContentText("Location:");
        dialog.showAndWait().ifPresent(location -> {
            DNode<District> districtDNode = district.getHead().getNext();
            while (districtDNode != null) {
                Node<Location> locationNode = districtDNode.getData().getLocationList().getHead().getNext();
                while (locationNode != null) {
                    if (locationNode.getData().getLocation().compareTo(location) == 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Location Found", "Location " + location + " found.");
                        break;
                    }
                    locationNode = locationNode.getNext();
                }

                districtDNode = districtDNode.getNext();
            }
            if (districtDNode == null) {
                showAlert(Alert.AlertType.ERROR, "Location Not Found", "Location " + location + " not found.");
            }
        });
    }

    private void InsertMartyrScene(ActionEvent e) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Insert Martyr");
        dialog.setHeaderText("Enter the District to insert martyr");
        dialog.setContentText("District:");
        dialog.showAndWait().ifPresent(districtName -> {

            DNode<District> targetDistrict = district.find(new District(districtName));
            if (targetDistrict != null) {
                TextInputDialog LocationDialog = new TextInputDialog();
                LocationDialog.setTitle("Insert Location");
                LocationDialog.setHeaderText("Enter the Location's name");
                LocationDialog.setContentText("Name of Location:");
                LocationDialog.showAndWait().ifPresent(location -> {
                    if (targetDistrict.getData().getLocationList().find(new Location(location)) != null) {
                        TextInputDialog nameDialog = new TextInputDialog();
                        Node<Location> locationNode = targetDistrict.getData().getLocationList().find(new Location(location));
                        nameDialog.setTitle("Insert Martyr");
                        nameDialog.setHeaderText("Enter the martyr's name");
                        nameDialog.setContentText("Name:");
                        nameDialog.showAndWait().ifPresent(data -> {
                            String[] information = data.split(",");
                            locationNode.getData().getMartyrsList().insert(new Martyr(information[0], LocalDate.parse(information[1]), Integer.parseInt(information[2]), information[3], information[4], information[5]));
                            showAlert(Alert.AlertType.INFORMATION, "Martyr Inserted", "Martyr " + information[0] + " has been inserted in the " + location + " location.");
                        });
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Location Not Found", "Location " + location + " not found.");
                    }
                });
            } else {
                showAlert(Alert.AlertType.ERROR, "District Not Found", "District " + districtName + " not found.");
            }
        });
    }

    private void updateMartyrScene(ActionEvent e) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Martyr");
        dialog.setHeaderText("Enter the District to update martyr");
        dialog.setContentText("District:");
        dialog.showAndWait().ifPresent(DistrictName -> {
            DNode<District> targetDistrict = district.find(new District(DistrictName));
            if (targetDistrict != null) {
                TextInputDialog locationDialog = new TextInputDialog();
                locationDialog.setTitle("Update Martyr");
                locationDialog.setHeaderText("Enter the location to update martyr");
                locationDialog.setContentText("Location:");
                locationDialog.showAndWait().ifPresent(location -> {
                    if (targetDistrict.getData().getLocationList().find(new Location(location)) != null) {
                        TextInputDialog nameDialog = new TextInputDialog();
                        nameDialog.setTitle("Update Martyr");
                        nameDialog.setHeaderText("Enter the martyr's name to update");
                        nameDialog.setContentText("Name:");
                        nameDialog.showAndWait().ifPresent(data -> {
                            Node<Location> locationNode = targetDistrict.getData().getLocationList().find(new Location(location));
                            Node<Martyr> targetMartyr = locationNode.getData().getMartyrsList().getHead().getNext();
                            while (targetMartyr != null && !targetMartyr.getData().getName().contains(data)) {
                                targetMartyr = targetMartyr.getNext();
                            }
                            if (targetMartyr != null) {
                                targetMartyr.getData().setName(data);
                                showAlert(Alert.AlertType.INFORMATION, "Martyr Updated", "Martyr " + data + " has been updated in the " + location + " location.");
                            } else {
                                showAlert(Alert.AlertType.ERROR, "Martyr Not Found", "Martyr " + data + " not found.");
                            }
                        });
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Location Not Found", "Location " + location + " not found.");
                    }
                });
            } else {
                showAlert(Alert.AlertType.ERROR, "District Not Found", "District " + DistrictName + " not found.");
            }
        });
    }

    private void deleteMartyrScene(ActionEvent e) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Martyr");
        dialog.setHeaderText("Enter the location to delete martyr");
        dialog.setContentText("District:");
        dialog.showAndWait().ifPresent(DistrictName -> {
            DNode<District> targetDistrict = district.find(new District(DistrictName));
            if (targetDistrict != null) {
                TextInputDialog locationDialog = new TextInputDialog();
                locationDialog.setTitle("Delete Martyr");
                locationDialog.setHeaderText("Enter the location to delete martyr");
                locationDialog.setContentText("Location:");
                locationDialog.showAndWait().ifPresent(location -> {
                    if (targetDistrict.getData().getLocationList().find(new Location(location)) != null) {
                        TextInputDialog nameDialog = new TextInputDialog();
                        nameDialog.setTitle("Delete Martyr");
                        nameDialog.setHeaderText("Enter the martyr's name to delete");
                        nameDialog.setContentText("Name:");
                        nameDialog.showAndWait().ifPresent(data -> {
                            Node<Location> targetLocation = targetDistrict.getData().getLocationList().find(new Location(location));
                            Node<Martyr> targetMartyr = targetLocation.getData().getMartyrsList().getHead().getNext();
                            while (targetMartyr != null && !targetMartyr.getData().getName().contains(data)) {
                                targetMartyr = targetMartyr.getNext();
                            }
                            if (targetMartyr != null) {
                                targetLocation.getData().getMartyrsList().delete(targetMartyr.getData());
                                showAlert(Alert.AlertType.INFORMATION, "Martyr Deleted", "Martyr " + data + " has been deleted in the " + location + " location.");
                            } else {
                                showAlert(Alert.AlertType.ERROR, "Martyr Not Found", "Martyr " + data + " not found.");
                            }
                        });
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Location Not Found", "Location " + location + " not found.");
                    }
                });
            } else {
                showAlert(Alert.AlertType.ERROR, "District Not Found", "District " + DistrictName + " not found.");
            }
        });
    }


    private void searchMartyr(ActionEvent e) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Martyr");
        dialog.setHeaderText("Enter the District to search martyr");
        dialog.setContentText("District:");
        dialog.showAndWait().ifPresent(districtName -> {
            DNode<District> targetDistrict = district.find(new District(districtName));
            if (targetDistrict != null) {
                TextInputDialog locationDialog = new TextInputDialog();
                locationDialog.setTitle("location to search");
                locationDialog.setHeaderText("Enter the location to search martyr");
                locationDialog.setContentText("Location:");
                locationDialog.showAndWait().ifPresent(location -> {
                    if (targetDistrict.getData().getLocationList().find(new Location(location)) != null) {
                        showAlert(Alert.AlertType.INFORMATION, "Location Found", "Location " + location + " found.");
                        TextInputDialog nameDialog = new TextInputDialog();
                        nameDialog.setTitle("Search Martyr");
                        nameDialog.setHeaderText("Enter the martyr's name to search");
                        nameDialog.setContentText("Name:");
                        nameDialog.showAndWait().ifPresent(data -> {
                            Node<Location> locationNode = targetDistrict.getData().getLocationList().find(new Location(location));
                            while (locationNode != null) {
                                Node<Martyr> martyrNode = locationNode.getData().getMartyrsList().getHead().getNext();
                                while (martyrNode != null) {
                                    if (martyrNode.getData().getName().contains(data)) {
                                        showAlert(Alert.AlertType.INFORMATION, "Martyr Found", "Martyr " + data + " found in the " + location + " location.");
                                    }
                                    martyrNode = martyrNode.getNext();
                                }
                                locationNode = locationNode.getNext();
                            }
                            showAlert(Alert.AlertType.ERROR, "Martyr Not Found", "Martyr " + data + " not found.");
                        });
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Location Not Found", "Location " + location + " not found.");
                    }
                });
            } else {
                showAlert(Alert.AlertType.ERROR, "District Not Found", "  " + districtName + " not found.");

            }
        });
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public BorderPane getBpPrimary() {
        return bpPrimary;
    }
}

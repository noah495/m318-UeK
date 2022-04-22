package m318.controllers;

import com.jfoenix.controls.JFXListView;
import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import m318.AutoCompletion;
import m318.wrapper.ITransport;
import m318.wrapper.entities.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class locationsController {

    ITransport iTransport = new ITransport();

    Stage stage;
    Parent root;

    @FXML
    TextField stationNameInput;
    @FXML
    Label invalidInputLabel;
    @FXML
    Button searchButton;
    @FXML
    ListView<Object> stationsList;
    @FXML
    JFXListView<String> autoCompletionList;

    public void getStations() throws IOException {
        if (stationNameInput.getText().isEmpty()) {
            invalidInputLabel.setVisible(true);
        }
        else {
            invalidInputLabel.setVisible(false);
            invalidInputLabel.setVisible(stationNameInput.getText().isEmpty());
            stationsList.getItems().clear();

            Stations stations = iTransport.getStations(stationNameInput.getText());

            if (stations.stationsList.isEmpty()) {
                stationsList.getItems().add("No Stations found :(");
            }
            else {
                for (final Station s : stations.stationsList) {
                    String imgUrl = "file:src/main/icons/" + s.Icon + ".png";
                    ImageView iconImage = new ImageView(imgUrl);
                    iconImage.setFitHeight(50.0);
                    iconImage.setFitWidth(50.0);
                    Label idLabel = new Label(s.Id);
                    Label nameLabel = new Label(s.Name);
                    Hyperlink mapStation = new Hyperlink("View on Maps");
                    mapStation.setOnAction(new EventHandler<ActionEvent> () {
                        @Override
                        public void handle(ActionEvent event){
                            openWebpage("https://www.google.com/maps/place/" + s.Coordinate.XCoordinate + "+" + s.Coordinate.YCoordinate);
                        }
                    });

                    GridPane gridPane = new GridPane();

                    gridPane.getColumnConstraints().add(new ColumnConstraints());
                    gridPane.getColumnConstraints().add(new ColumnConstraints());
                    gridPane.getColumnConstraints().add(new ColumnConstraints());
                    gridPane.getColumnConstraints().add(new ColumnConstraints());

                    gridPane.getColumnConstraints().get(0).setPercentWidth(20);
                    gridPane.getColumnConstraints().get(1).setPercentWidth(30);
                    gridPane.getColumnConstraints().get(2).setPercentWidth(30);
                    gridPane.getColumnConstraints().get(3).setPercentWidth(20);

                    gridPane.add(iconImage, 0, 0);
                    gridPane.add(idLabel, 1, 0);
                    gridPane.add(nameLabel, 2, 0);
                    gridPane.add(mapStation, 3, 0);

                    stationsList.getItems().add(gridPane);
                }
            }
        }
    }

    public static boolean openWebpage(String uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(URI.create(uri));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public void loadPage(ActionEvent event) throws IOException {
        stage = (Stage) searchButton.getScene().getWindow();
        String menuItem = event.getSource().toString();
        if (menuItem.contains("connections")) {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("connections.fxml"));
        }
        if (menuItem.contains("locations")) {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("locations.fxml"));
        }
        if (menuItem.contains("stationBoard")) {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("stationBoard.fxml"));
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void recommendedStationClicked(MouseEvent mouseEvent) {
        stationNameInput.setText(autoCompletionList.getSelectionModel().getSelectedItem());
        autoCompletionList.setVisible(false);
    }

    public void getStationText(KeyEvent keyEvent) throws IOException {
        AutoCompletion autoCompletion = new AutoCompletion();

        autoCompletionList.getItems().clear();
        autoCompletion.stationList.clear();
        ArrayList<Station> stations = autoCompletion.getRecommendedStations(stationNameInput.getText());
        for (Station s : stations) {
            autoCompletionList.getItems().add(s.Name);
        }
        if (autoCompletion.stationList.isEmpty()) {
            autoCompletionList.setVisible(false);
        } else {
            autoCompletionList.setVisible(true);
        }
    }

}

package m318.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;
import m318.AutoCompletion;
import m318.wrapper.entities.Connection;
import m318.wrapper.entities.Connections;
import m318.wrapper.ITransport;
import m318.wrapper.entities.Station;

import java.io.IOException;
import java.util.ArrayList;

public class connectionsController {

    ITransport iTransport = new ITransport();

    private boolean isAdvancedSearch = false;

    @FXML
    TextField startStation;
    @FXML
    TextField endStation;
    @FXML
    Button searchButton;
    @FXML
    Button advancedSearchButton;
    @FXML
    JFXTimePicker timePicker;
    @FXML
    JFXDatePicker datePicker;
    @FXML
    ListView<GridPane> connectionsList;
    @FXML
    JFXListView<String> fromStationCompletionList;
    @FXML
    JFXListView<String> toStationCompletionList;

    public void getConnections() throws IOException {
        connectionsList.getItems().clear();
        Connections connections = null;

        if(timePicker.getValue() == null || datePicker.getValue() == null || !isAdvancedSearch){
             connections = iTransport.getConnections(startStation.getText(), endStation.getText());
        }
        else{
            connections = iTransport.getConnectionsByTime(startStation.getText(), endStation.getText(), String.format("%1$tY-%1$tm-%1$td", datePicker.getValue()), String.format("%1$tH:%1$tM", timePicker.getValue()));
        }
        for (Connection c: connections.ConnectionList){
            GridPane gridPane = getGridPane(c);
            connectionsList.getItems().add(gridPane);
        }
    }

    public void advancedSearch() {
        if(!isAdvancedSearch){
            isAdvancedSearch = true;
            timePicker.setVisible(true);
            datePicker.setVisible(true);
        }
        else{
            isAdvancedSearch = false;
            timePicker.setVisible(false);
            datePicker.setVisible(false);
        }
    }

    public void getFromText(KeyEvent keyEvent) throws IOException {
        AutoCompletion autoCompletion = new AutoCompletion();

        fromStationCompletionList.getItems().clear();
        autoCompletion.stationList.clear();
        ArrayList<Station> stations = autoCompletion.getRecommendedStations(startStation.getText());
        for (Station s : stations) {
            fromStationCompletionList.getItems().add(s.Name);
        }
        if (autoCompletion.stationList.isEmpty()) {
            fromStationCompletionList.setVisible(false);
        } else {
            fromStationCompletionList.setVisible(true);
        }
    }

    public void getToText(KeyEvent keyEvent) throws IOException {
        AutoCompletion autoCompletion = new AutoCompletion();

        toStationCompletionList.getItems().clear();
        autoCompletion.stationList.clear();
        ArrayList<Station> stations = autoCompletion.getRecommendedStations(endStation.getText());
        for (Station s : stations) {
            toStationCompletionList.getItems().add(s.Name);
        }
        if (autoCompletion.stationList.isEmpty()) {
            toStationCompletionList.setVisible(false);
        } else {
            toStationCompletionList.setVisible(true);
        }
    }

    public void recommendedFromStationClicked(MouseEvent mouseEvent) {
        startStation.setText(fromStationCompletionList.getSelectionModel().getSelectedItem());
        fromStationCompletionList.setVisible(false);
    }

    public void recommendedToStationClicked(MouseEvent mouseEvent) {
        endStation.setText(toStationCompletionList.getSelectionModel().getSelectedItem());
        toStationCompletionList.setVisible(false);
    }

    public String transformDate(String dateString){
        String newString = dateString;
        newString.replace("T", " ");
        newString.replace("+2000", "");
        return newString;
    }

    private GridPane getGridPane(Connection c) {
        GridPane gridPane = new GridPane();

        gridPane.getColumnConstraints().add(new ColumnConstraints());
        gridPane.getColumnConstraints().add(new ColumnConstraints());
        gridPane.getColumnConstraints().add(new ColumnConstraints());
        gridPane.getColumnConstraints().add(new ColumnConstraints());

        gridPane.getColumnConstraints().get(0).setPercentWidth(10);
        gridPane.getColumnConstraints().get(1).setPercentWidth(20);
        gridPane.getColumnConstraints().get(2).setPercentWidth(50);
        gridPane.getColumnConstraints().get(3).setPercentWidth(20);

        gridPane.getRowConstraints().add(new RowConstraints());
        gridPane.getRowConstraints().add(new RowConstraints());
        gridPane.getRowConstraints().add(new RowConstraints());

        gridPane.getRowConstraints().get(0).setPercentHeight(20);
        gridPane.getRowConstraints().get(0).setPercentHeight(60);
        gridPane.getRowConstraints().get(0).setPercentHeight(20);

        //TODO: Fix Images (icon returning null ?!)
        ImageView iconImage = new ImageView("file:src/main/icons/train.png");
        iconImage.setFitHeight(50.0);
        iconImage.setFitWidth(50.0);
        Label startNameLabel = new Label(c.From.Station.Name);
        Label startDepartureLabel = new Label(c.From.Departure);
        Label endArrivalLabel = new Label(c.To.Arrival);
        Label endNameLabel = new Label(c.To.Station.Name);
        Label durationLabel = new Label(c.Duration);
        Label arrowLabel = new Label("------------------------------------->");

        startNameLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        startDepartureLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        endArrivalLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        endNameLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        durationLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        arrowLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        gridPane.add(iconImage, 0,1);
        gridPane.add(startNameLabel, 1,1);
        gridPane.add(startDepartureLabel, 1,2);
        gridPane.add(endArrivalLabel,3,2);
        gridPane.add(endNameLabel, 3,1);
        gridPane.add(durationLabel, 2,0);
        gridPane.add(arrowLabel,2,1);
        arrowLabel.setTextAlignment(TextAlignment.CENTER);
        return gridPane;
    }
}
//TODO: Refactor --> Clean Code
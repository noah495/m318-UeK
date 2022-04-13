package m318.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import m318.AutoCompletion;
import m318.wrapper.entities.Connection;
import m318.wrapper.entities.Connections;
import m318.wrapper.ITransport;
import m318.wrapper.entities.Station;

import java.io.IOException;
import java.util.ArrayList;

public class connectionsController {

    ITransport iTransport = new ITransport();
    AutoCompletion autoCompletion = new AutoCompletion();

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
    JFXListView<String> completionList;



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
            Label nameLabel = new Label(c.From.Station.Name);
            Label fromLabel = new Label(c.From.Departure);
            Label toLabel = new Label(c.To.Arrival);
            Label durationLabel = new Label(c.Duration);

            GridPane gridPane = new GridPane();

            gridPane.add(nameLabel, 0,0);
            gridPane.add(fromLabel, 1,0);
            gridPane.add(toLabel, 2,0);
            gridPane.add(durationLabel, 3,0);
            /*gridPane.getColumnConstraints().get(0).setPercentWidth(20);
            gridPane.getColumnConstraints().get(1).setPercentWidth(30);
            gridPane.getColumnConstraints().get(2).setPercentWidth(50);*/


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

        completionList.getItems().clear();
        autoCompletion.stationList.clear();
        ArrayList<Station> stations = autoCompletion.getRecommendedStations(startStation.getText());
        for (Station s : stations) {
            completionList.getItems().add(s.Name);
        }
        if (autoCompletion.stationList.isEmpty()) {
            completionList.setVisible(false);
        } else {
            completionList.setVisible(true);
        }
    }
}

package m318.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import m318.wrapper.entities.Connection;
import m318.wrapper.entities.Connections;
import m318.wrapper.ITransport;

import java.io.IOException;

public class connectionController {

    ITransport iTransport = new ITransport();

    @FXML
    TextField startStation;
    @FXML
    TextField endStation;
    @FXML
    Button searchButton;
    @FXML
    ListView<GridPane> connectionsList;

    public void getConnections() throws IOException {
        connectionsList.getItems().clear();
        Connections connections = iTransport.getConnections(startStation.getText(), endStation.getText());
        System.out.println(connections);

        for (Connection c: connections.ConnectionList){
            Label fromLabel = new Label(c.From.Departure);
            Label toLabel = new Label(c.To.Arrival);
            Label durationLabel = new Label(c.Duration);

            GridPane gridPane = new GridPane();
            gridPane.add(fromLabel, 0,0);
            gridPane.add(toLabel, 1,0);
            gridPane.add(durationLabel, 2,0);
            /*gridPane.getColumnConstraints().get(0).setPercentWidth(20);
            gridPane.getColumnConstraints().get(1).setPercentWidth(30);
            gridPane.getColumnConstraints().get(2).setPercentWidth(50);*/


            connectionsList.getItems().add(gridPane);
        }
    }
}

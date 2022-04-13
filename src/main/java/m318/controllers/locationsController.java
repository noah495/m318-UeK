package m318.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import m318.wrapper.HttpClient;
import m318.wrapper.ITransport;
import m318.wrapper.entities.*;

import java.io.IOException;

public class locationsController {
    ITransport iTransport = new ITransport();

    @FXML
    TextField stationNameInput;
    @FXML
    Button searchButton;
    @FXML
    ListView<GridPane> stationsList;

    public void getStations() throws IOException {
        stationsList.getItems().clear();

        Stations stations = iTransport.getStations(stationNameInput.getText());

        for(Station s : stations.stationsList){
            Label iconLabel = new Label(s.Icon);
            Label idLabel = new Label(s.Id);
            Label nameLabel = new Label(s.Name);
            Label coordinateLabel = new Label(s.Coordinate.toString());

            GridPane gridPane = new GridPane();
            gridPane.add(iconLabel, 0,0);
            gridPane.add(idLabel, 1,0);
            gridPane.add(nameLabel, 2,0);
            /*gridPane.getColumnConstraints().get(0).setPercentWidth(20);
            gridPane.getColumnConstraints().get(1).setPercentWidth(30);
            gridPane.getColumnConstraints().get(2).setPercentWidth(50);*/


            stationsList.getItems().add(gridPane);
        }

    }

}

package m318.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import m318.wrapper.ITransport;
import m318.wrapper.entities.StationBoard;
import m318.wrapper.entities.StationBoardRoot;

import java.io.IOException;

public class stationBoardController {

    ITransport iTransport = new ITransport();

    @FXML
    TextField stationNameInput;
    @FXML
    Button searchButton;
    @FXML
    ListView stationBoardList;

    public void getStationBoard() throws IOException {
        stationBoardList.getItems().clear();
        StationBoardRoot stationBoardRoot = iTransport.getStationBoard(stationNameInput.getText());

        for (StationBoard s : stationBoardRoot.StationBoard){
            Label nameLabel = new Label(s.Name);
            Label numberLabel = new Label(s.Number);
            Label toLabel = new Label(s.To);

            GridPane gridPane = new GridPane();
            gridPane.add(nameLabel, 0,0);
            gridPane.add(numberLabel, 1,0);
            gridPane.add(toLabel, 2,0);
            /*gridPane.getColumnConstraints().get(0).setPercentWidth(20);
            gridPane.getColumnConstraints().get(1).setPercentWidth(30);
            gridPane.getColumnConstraints().get(2).setPercentWidth(50);*/


            stationBoardList.getItems().add(gridPane);
        }
    }
}

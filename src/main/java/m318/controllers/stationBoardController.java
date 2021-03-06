package m318.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import m318.AutoCompletion;
import m318.wrapper.ITransport;
import m318.wrapper.entities.Station;
import m318.wrapper.entities.StationBoard;
import m318.wrapper.entities.StationBoardRoot;

import java.io.IOException;
import java.util.ArrayList;

public class stationBoardController {

    ITransport iTransport = new ITransport();

    Stage stage;
    Parent root;

    @FXML
    TextField stationName;
    @FXML
    Label invalidInputLabel;
    @FXML
    Button searchButton;
    @FXML
    ListView<Object> stationBoardList;
    @FXML
    JFXListView<String> stationCompletionList;

    public void getStationBoard() throws IOException {
        if(stationName.getText().isEmpty()){
            invalidInputLabel.setVisible(true);
        }
        else{
            invalidInputLabel.setVisible(false);

            stationBoardList.getItems().clear();
            StationBoardRoot stationBoardRoot = iTransport.getStationBoard(stationName.getText());
            if(stationBoardRoot.StationBoard.isEmpty()){
                stationBoardList.getItems().add("No Results Found :(");
            }
            else{
                for (StationBoard s : stationBoardRoot.StationBoard){
                    GridPane gridPane = getGridPane(s);
                    stationBoardList.getItems().add(gridPane);
                }
            }
        }
    }

    public void getStationText(KeyEvent keyEvent) throws IOException {
        AutoCompletion autoCompletion = new AutoCompletion();

        stationCompletionList.getItems().clear();
        autoCompletion.stationList.clear();
        ArrayList<Station> stations = autoCompletion.getRecommendedStations(stationName.getText());
        for (Station s : stations) {
            stationCompletionList.getItems().add(s.Name);
        }
        stationCompletionList.setVisible(!autoCompletion.stationList.isEmpty());
    }

    public void recommendedStationClicked(MouseEvent mouseEvent) {
        stationName.setText(stationCompletionList.getSelectionModel().getSelectedItem());
        stationCompletionList.setVisible(false);
    }

    private GridPane getGridPane(StationBoard s) {
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

        Label stationDepartureLabel = new Label(s.Stop.Platform);
        Label stationNameLabel = new Label(s.Name);
        Label startDepartureLabel = new Label(s.Stop.Departure);
        Label endNameLabel = new Label(s.To);
        Label arrowLabel = new Label("------------------------------------->");

        stationNameLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        startDepartureLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        endNameLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        arrowLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        gridPane.add(stationDepartureLabel, 0,1);
        gridPane.add(stationNameLabel, 1,1);
        gridPane.add(startDepartureLabel, 1,2);
        gridPane.add(endNameLabel, 3,1);
        gridPane.add(arrowLabel,2,1);
        arrowLabel.setTextAlignment(TextAlignment.CENTER);
        return gridPane;
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
}

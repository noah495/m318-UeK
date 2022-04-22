package m318.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import m318.AutoCompletion;
import m318.MailService;
import m318.wrapper.entities.Connection;
import m318.wrapper.entities.Connections;
import m318.wrapper.ITransport;
import m318.wrapper.entities.Station;

import java.io.IOException;
import java.util.ArrayList;

public class connectionsController {

    Stage stage;
    Parent root;

    ITransport iTransport = new ITransport();
    MailService mailService = new MailService();

    private boolean isAdvancedSearch = false;

    @FXML
    TextField startStation;
    @FXML
    TextField endStation;
    @FXML
    Label fromInputError;
    @FXML
    Label toInputError;
    @FXML
    Button searchButton;
    @FXML
    Button advancedSearchButton;
    @FXML
    JFXTimePicker timePicker;
    @FXML
    JFXDatePicker datePicker;
    @FXML
    ListView<Object> connectionsList;
    @FXML
    JFXListView<String> fromStationCompletionList;
    @FXML
    JFXListView<String> toStationCompletionList;

    public void getConnections() throws IOException {
        if (startStation.getText().isEmpty() || endStation.getText().isEmpty()) {
            fromInputError.setVisible(startStation.getText().isEmpty());
            toInputError.setVisible(endStation.getText().isEmpty());
        }
        else {
            fromInputError.setVisible(false);
            toInputError.setVisible(false);
            connectionsList.getItems().clear();
            Connections connections = null;

            if (timePicker.getValue() == null || datePicker.getValue() == null || !isAdvancedSearch) {
                connections = iTransport.getConnections(startStation.getText(), endStation.getText());
            } else {
                connections = iTransport.getConnectionsByTime(startStation.getText(), endStation.getText(), String.format("%1$tY-%1$tm-%1$td", datePicker.getValue()), String.format("%1$tH:%1$tM", timePicker.getValue()));
            }

            if(connections.ConnectionList.isEmpty()){
              connectionsList.getItems().add("No Connections found :(");
            }
            else {
                for (Connection c : connections.ConnectionList) {
                    GridPane gridPane = getGridPane(c);
                    connectionsList.getItems().add(gridPane);
                }
            }
        }
    }

    public void advancedSearch(ActionEvent event) {
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

    private GridPane getGridPane(final Connection c) {
        final GridPane gridPane = new GridPane();

        gridPane.getColumnConstraints().add(new ColumnConstraints());
        gridPane.getColumnConstraints().add(new ColumnConstraints());
        gridPane.getColumnConstraints().add(new ColumnConstraints());
        gridPane.getColumnConstraints().add(new ColumnConstraints());
        gridPane.getColumnConstraints().add(new ColumnConstraints());

        gridPane.getColumnConstraints().get(0).setPercentWidth(10);
        gridPane.getColumnConstraints().get(1).setPercentWidth(20);
        gridPane.getColumnConstraints().get(2).setPercentWidth(30);
        gridPane.getColumnConstraints().get(3).setPercentWidth(20);
        gridPane.getColumnConstraints().get(4).setPercentWidth(20);

        gridPane.getRowConstraints().add(new RowConstraints());
        gridPane.getRowConstraints().add(new RowConstraints());
        gridPane.getRowConstraints().add(new RowConstraints());

        gridPane.getRowConstraints().get(0).setPercentHeight(20);
        gridPane.getRowConstraints().get(0).setPercentHeight(60);
        gridPane.getRowConstraints().get(0).setPercentHeight(20);

        ImageView iconImage = new ImageView("file:src/main/icons/unknown.png");
        iconImage.setFitHeight(50.0);
        iconImage.setFitWidth(50.0);
        Label startNameLabel = new Label(c.From.Station.Name);
        Label startDepartureLabel = new Label(c.From.Departure);
        Label endArrivalLabel = new Label(c.To.Arrival);
        Label endNameLabel = new Label(c.To.Station.Name);
        Label durationLabel = new Label(c.Duration);
        Label arrowLabel = new Label("------------------------------------->");
        final Button mailButton = new Button("Send Via Mail");

        connectionToMail(c, mailButton);

        startNameLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        startDepartureLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        endArrivalLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        endNameLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        durationLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        arrowLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        mailButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        gridPane.add(iconImage, 0,1);
        gridPane.add(startNameLabel, 1,1);
        gridPane.add(startDepartureLabel, 1,2);
        gridPane.add(endArrivalLabel,3,2);
        gridPane.add(endNameLabel, 3,1);
        gridPane.add(durationLabel, 2,0);
        gridPane.add(arrowLabel,2,1);
        gridPane.add(mailButton,4,1);
        arrowLabel.setTextAlignment(TextAlignment.CENTER);
        return gridPane;
    }

    private void connectionToMail(final Connection c, final Button mailButton) {
        mailButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
             final Popup popUp = new Popup();

                final TextArea mailAdr = new TextArea();
                mailAdr.setPromptText("Email of Receiver");

                Button sendBtn = new Button("Send Mail");
                sendBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                sendBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            mailService.sendConnection(mailAdr.getText(), c);
                            popUp.hide();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Button closeBtn = new Button("Close");
                closeBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                closeBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        popUp.hide();
                    }
                });

                GridPane gridPaneMail = new GridPane();
                gridPaneMail.getRowConstraints().add(new RowConstraints());
                gridPaneMail.getRowConstraints().add(new RowConstraints());
                gridPaneMail.getRowConstraints().get(0).setPercentHeight(80);
                gridPaneMail.getRowConstraints().get(1).setPercentHeight(20);

                gridPaneMail.getColumnConstraints().add(new ColumnConstraints());
                gridPaneMail.getColumnConstraints().add(new ColumnConstraints());
                gridPaneMail.getColumnConstraints().get(0).setPercentWidth(50);
                gridPaneMail.getColumnConstraints().get(1).setPercentWidth(50);

                gridPaneMail.add(sendBtn, 0,1);
                gridPaneMail.add(closeBtn, 1,1);
                gridPaneMail.add(mailAdr,0 ,0,2,1);

                popUp.getContent().add(gridPaneMail);

                popUp.show(mailButton.getScene().getWindow());
            }
        });
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
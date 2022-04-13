package m318.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.DatePicker;
import m318.wrapper.entities.*;

import java.io.IOException;


public class ITransport {
    private HttpClient httpClient = new HttpClient();

    private String baseUrl = "http://transport.opendata.ch/v1/";

    public Stations getStations(String query) throws IOException {
        String jsonText;
        jsonText = httpClient.doRequest(baseUrl + "locations?query=" + query + "&type=station");

        ObjectMapper objectMapper = new ObjectMapper();
        Stations stations = null;

        try {
            stations = objectMapper.readValue(jsonText, Stations.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return stations;
    }

    public Connections getConnections(String startStation, String endStation) throws IOException{
        String jsonText;
        jsonText = httpClient.doRequest(baseUrl + "connections?from=" + startStation + "&to=" + endStation);

        ObjectMapper objectMapper = new ObjectMapper();
        Connections connections = null;

        try{
            connections = objectMapper.readValue(jsonText, Connections.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return connections;
    }


    public Connections getConnectionsByTime(String startStation, String endStation, String date, String time) throws IOException {
        String jsonText;
        jsonText = httpClient.doRequest(baseUrl + "connections?from=" + startStation + "&to=" + endStation + "&date=" + date + "&time" + time);

        ObjectMapper objectMapper = new ObjectMapper();
        Connections connections = null;

        try{
            connections = objectMapper.readValue(jsonText, Connections.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return connections;
    }

    public StationBoardRoot getStationBoard(String stationName) throws IOException {
        String jsonText;
        jsonText = httpClient.doRequest(baseUrl + "stationboard?station=" + stationName);

        ObjectMapper objectMapper = new ObjectMapper();
        StationBoardRoot stationBoardRoot = null;

        try{
            stationBoardRoot = objectMapper.readValue(jsonText, StationBoardRoot.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return stationBoardRoot;
    }
}



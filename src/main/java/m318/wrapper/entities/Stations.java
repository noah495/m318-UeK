package m318.wrapper.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Stations {

    @JsonProperty("stations")
    public ArrayList<Station> stationsList;
}

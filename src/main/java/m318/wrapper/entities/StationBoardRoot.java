package m318.wrapper.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class StationBoardRoot {

    @JsonProperty("station")
    public Station Station;

    @JsonProperty("stationboard")
    public ArrayList<StationBoard> StationBoard;
}

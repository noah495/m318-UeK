package m318.wrapper.entities;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Station {

    @JsonProperty("id")
    public String Id;

    @JsonProperty("name")
    public String Name;

    @JsonProperty("score")
    public int Score;

    @JsonProperty("coordinate")
    public Coordinate Coordinate;

    @JsonProperty("distance")
    public double Distance;

    @JsonProperty("icon")
    public String Icon;
}

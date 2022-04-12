package m318.wrapper.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate {

    @JsonProperty("type")
    public String Type;

    @JsonProperty("x")
    public double XCoordinate;

    @JsonProperty("y")
    public double YCoordinate;

}

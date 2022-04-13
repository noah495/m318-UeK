package m318.wrapper.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class StationBoard {

    @JsonProperty("name")
    public String Name;

    @JsonProperty("number")
    public String Number;

    @JsonProperty("to")
    public String To;

    @JsonProperty("operator")
    public String Operator;

    @JsonProperty("stop")
    public Stop Stop;
}


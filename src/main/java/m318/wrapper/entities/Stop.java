package m318.wrapper.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Stop {

    @JsonProperty("departure")
    public String Departure;

    @JsonProperty("platform")
    public String Platform;

}

package m318.wrapper.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Connection {

    @JsonProperty("duration")
    public String Duration;

    @JsonProperty("to")
    public ConnectionPoint To;

    @JsonProperty("from")
    public ConnectionPoint From;
}

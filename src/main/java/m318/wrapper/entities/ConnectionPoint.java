package m318.wrapper.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ConnectionPoint {

    @JsonProperty("station")
    public Station Station;

    @JsonProperty("arrival")
    public String Arrival;

    @JsonProperty("arrivalTimestamp")
    public String ArrivalTimeStamp;

    @JsonProperty("departure")
    public String Departure;

    @JsonProperty("departureTimestamp")
    public String DepartureTimestamp;

    @JsonProperty("delay")
    public int Delay;

    @JsonProperty("platform")
    public String Platform;

    @JsonProperty("realtimeAvailability")
    public String RealtimeAvailability;
}

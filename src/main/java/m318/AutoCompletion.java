package m318;
import m318.wrapper.ITransport;
import m318.wrapper.entities.Station;
import m318.wrapper.entities.Stations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutoCompletion {
    ITransport iTransport = new ITransport();
    public ArrayList<Station> stationList = new ArrayList<Station>();

    public ArrayList<Station> getRecommendedStations(String query) throws IOException {

        if (stationList.isEmpty()) {
            Stations stations = iTransport.getStations(query);
            stationList.addAll(stations.stationsList);
        }
        else {
            for(Station s : stationList){
                if(s.Name.contains(query)){
                    stationList.remove(s.Name);
                }
            }
        }
        return stationList;
    }
}

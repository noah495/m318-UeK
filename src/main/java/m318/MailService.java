package m318;

import m318.wrapper.entities.Connection;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class MailService {

    public void sendConnection(String receiver, Connection connection) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        String subject = "Connection from " + connection.From.Station.Name + " to " + connection.To.Station.Name;
        String body = "From: " + connection.From.Station.Name + ";  Departure: " + connection.From.Departure +
                "%0D%0A To: " + connection.To.Station.Name + ";  Arrival: " + connection.To.Arrival +
                "%0D%0A Duration: " + connection.Duration;
        String message = "mailto:"+ receiver +"?subject=" + subject + "&body=" + body;
        URI uri = URI.create(transformRequest(message));
        desktop.mail(uri);
    }

    private String transformRequest(String request) {
        return request.replaceAll("\\s+", "%20");
    }

}

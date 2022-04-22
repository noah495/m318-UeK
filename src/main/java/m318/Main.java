package m318;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("connections.fxml"));
        primaryStage.setTitle("ESBEBE");
        primaryStage.setScene(new Scene(root, 960, 540));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

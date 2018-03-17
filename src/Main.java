

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.bluetooth.*;
import java.io.*;





public class Main  extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    /**
     * Start the JavaFX
     *
     * @param primaryStage The main Stage(window) of the application upon creation)
     * @throws IOException  if the Start.fxml is not found.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        Pane dummyRoot = new Pane();
        Scene mainScene = new Scene(dummyRoot, 600, 600);

        // Create, Initialize and show the stage
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Bluetooth Serial Terminal");
        primaryStage.setResizable(false);


        primaryStage.show();

        ViewController view = new ViewController();

        primaryStage.setOnCloseRequest( e ->{
            view.stop();
        });


        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        loader.setController(view);

        loader.setRoot(loader.load());

        mainScene.setRoot(loader.getRoot());
    }
}


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML
    private VBox displayContainer;

    @FXML
    private Label status;
    @FXML
    private TextField deviceInput;

    @FXML
    private ListView<String> terminalOutView;
    @FXML
    private Button btnConnect;

    private Bluetooth blue;
    private Thread t;
    private ObservableList<String> b;
    private Robot robot;

    @FXML
    private void btnConnectClicked(){
        if(btnConnect.getText().equals("Connect")){
            blue = new Bluetooth(status,deviceInput.getText(),b,robot);
            blue.start();
            btnConnect.setText("Disconnect");
        } else {
            stop();
            btnConnect.setText("Connect");
        }

    }

    public void stop(){
        blue.interrupt();
    }

    @FXML
    TextField terminalInput;

    @FXML
    private void btnSendClicked(){
        if(!terminalInput.getText().equals("")){
            blue.send(terminalInput.getText());
            terminalInput.setText("");
        }
    }

    @FXML
    private void btnSaveStreamClicked (){
        Stage s = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showSaveDialog(s);
        if (selectedFile != null) {
            blue.saveStream(selectedFile);
        }
    }

    @FXML
    private void btnClearClicked(){
        b.clear();
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> a = new ArrayList<>();
        b = FXCollections.observableArrayList(a);
        terminalOutView.setItems(b);
        robot = new Robot(displayContainer);
    }
}

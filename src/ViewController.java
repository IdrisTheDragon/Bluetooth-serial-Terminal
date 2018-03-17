
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.util.ArrayList;

public class ViewController {
    @FXML
    private Label status;
    @FXML
    private TextField deviceInput;

    @FXML
    private ListView<String> terminalOutView;
    @FXML
    private Button btnConnect;

    Bluetooth blue;

    Thread t;

    @FXML
    private void btnConnectClicked(){
        if(btnConnect.getText().equals("Connect")){
            ArrayList<String> a = new ArrayList<>();
            ObservableList<String> b = FXCollections.observableArrayList(a);
            terminalOutView.setItems(b);
            blue = new Bluetooth(status,deviceInput.getText(),b);
            blue.start();
            btnConnect.setText("Disconnect");
        } else {
            blue.interrupt();
            btnConnect.setText("Connect");
        }

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
}

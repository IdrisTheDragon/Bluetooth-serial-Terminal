import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import test.CustomCell;

import java.io.IOException;


public class Robot {
    private VBox root;
    public Robot(VBox root){
        this.root = root;

        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("Robot.fxml"));
        loader.setController(this);
        try {
            loader.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.getChildren().add(loader.getRoot());
    }

    @FXML
    private CustomCell c00,c01,c02,c03,c10,c11,c12,c13,c20,c21,c22,c23,c30,c31,c32,c33;
    private CustomCell[][] map = {{c00,c01,c02,c03},{c10,c11,c12,c13},{c20,c21,c22,c23},{c30,c31,c32,c33}};

    @FXML
    private Label r00,r10,r20,r01,r21,r02,r12,r22;

    public void parse(String lineRead) {
        switch(lineRead.charAt(0)){
            case 'S':
                parseSensor(lineRead);
                break;
            case 'C':
                parseCell(lineRead);
                break;
            default:
                break;
        }
    }

    private void parseCell(String lineRead) {

    }

    private void parseSensor(String lineRead) {
        switch(lineRead.charAt(1)){
            case 'R':
                String[] strArray = lineRead.split("_");
                //ignore 0
                r00.setText(strArray[1]);
                r10.setText(strArray[2]);
                r20.setText(strArray[3]);

                r21.setText(strArray[4]);

                r22.setText(strArray[5]);
                r12.setText(strArray[6]);
                r02.setText(strArray[7]);

                r01.setText(strArray[8]);
                break;
            default:
                break;
        }
    }
}

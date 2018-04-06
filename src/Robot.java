import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import test.CustomCell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Robot implements Initializable {
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
    private CustomCell[][] map;

    @FXML
    private Label r00,r10,r20,r01,r21,r02,r12,r22;

    @FXML
    private Label LS,LLS,LRS,EL,ER;

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
        String[] strArray = lineRead.split("_");
        int x = Integer.parseInt(strArray[1]);
        int y = Integer.parseInt(strArray[2]);

        map[x][y].setText(strArray[3]);
        boolean N = Integer.parseInt(strArray[4]) < 100;
        boolean S = Integer.parseInt(strArray[5]) < 100;
        boolean E = Integer.parseInt(strArray[6]) < 100;
        boolean W = Integer.parseInt(strArray[7]) < 100;

        map[x][y].setCell(N,S,W,E);
    }

    private void parseSensor(String lineRead) {
        String[] strArray = lineRead.split("_");
        switch(lineRead.charAt(1)){
            case 'R':
                r00.setText(strArray[1]);
                r10.setText(strArray[2]);
                r20.setText(strArray[3]);

                r21.setText(strArray[4]);

                r22.setText(strArray[5]);
                r12.setText(strArray[6]);
                r02.setText(strArray[7]);

                r01.setText(strArray[8]);
                break;
            case 'L':
                LS.setText(strArray[1]);
                break;
            case 'E':
                EL.setText(strArray[1]);
                ER.setText(strArray[2]);
                break;
            case 'B':
                LLS.setText(strArray[1]);
                LRS.setText(strArray[2]);
                break;
            default:
                break;
        }
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
        map = new CustomCell[][]{{c03, c02, c01, c00}, {c13, c12, c11, c10}, {c23, c22, c21, c20}, {c33, c32, c31, c30}};
    }
}

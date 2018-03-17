package test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;


public class CustomCell extends VBox {
    @FXML
    private Label l;

    @FXML
    Rectangle north,south,east,west;

    public CustomCell(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/test/CustomCell.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setText(String n){
        l.setText(n);
    }

    public void setCell(boolean northWall,boolean southWall,boolean westWall, boolean eastWall){
        north.setVisible(northWall);
        south.setVisible(southWall);
        east.setVisible(eastWall);
        west.setVisible(westWall);
    }
}

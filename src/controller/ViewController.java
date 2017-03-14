package controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    @FXML private GridPane gridPane;
    private NumberBinding gridWidthHeight;
    private int gridSize = 3;
    @FXML private AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loaded our main view");
        initGridPane(gridSize);
    }

    public void initGridPane(int gridSize){
        int columns = gridSize;
        int rows = gridSize;

        for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.SOMETIMES); //resize to screen
            gridPane.getColumnConstraints().add(column);
        }
        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(row);
        }

        for(int col=0; col<gridSize; col++){
            for(int row=0; row<gridSize; row++){
                Pane pane = new Pane();
                pane.setStyle(
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1px;");

                pane.getChildren().add(new Label(col +","+row));

                //Add listeners to this pain
                //When this pain is clicked
                pane.setOnMouseClicked(e->{
                    System.out.println("Row: "+ GridPane.getRowIndex(pane));
                    System.out.println("column: "+ GridPane.getColumnIndex(pane));
                });

                gridPane.add(pane,col,row);
            }
        }
    }
}

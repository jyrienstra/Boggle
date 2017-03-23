package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import model.Boggle;
import model.ChooseFile;
import model.Field;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ViewController implements Initializable {
    @FXML private GridPane gridPane;
    private int gridSize = 4;
    @FXML private TextField currentWordField;
    @FXML private HBox hBox;
    @FXML private VBox vBox;
    @FXML private TextArea foundWordsField;
    @FXML private TextArea currentAllowedWordsField;
    private ArrayList<Pane> selectedPanes;
    private ArrayList<String> wordList;
    private int wordsFound = 0;
    private ChooseFile chooseFile;

    private int clickedPaneColor = 255;
    private Stack<Pane> clickedPaneStack;

    Boggle boggle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foundWordsField.setEditable(false);
        foundWordsField.setMouseTransparent(true);
        foundWordsField.setFocusTraversable(false);

        boggle = new Boggle(gridSize);
        boggle.printGrid();

        //Choose wordlist from textfile(seperated by line)
        chooseFile = new ChooseFile();
        File file = new File("src/wordlist.txt"); //set default file to dutch
        chooseFile.setFile(file);
        this.wordList = chooseFile.getChosenFileInList();

        //Fix hbox position
        gridPane.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)->{
            hBox.setLayoutX(gridPane.getLayoutX() + 16);
            hBox.setLayoutY(gridPane.getLayoutY() + gridPane.getHeight() + 16);

        });
        gridPane.heightProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)->{
            hBox.setLayoutX(gridPane.getLayoutX() + 16);
            hBox.setLayoutY(gridPane.getLayoutY() + gridPane.getHeight() + 16);
        });

        System.out.println("Loaded our main view");
        init(gridSize);
    }

    /**
     * Solve the boggle
     */
    public void solveBoggle() {
        boggle.solveGrid();

        for(String foundWord : boggle.getFoundWords()) {
            this.foundWordsField.appendText(foundWord + "\n");
        }

    }

    public void initColumns(int columns){
        for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.SOMETIMES); //resize to screen
            gridPane.getColumnConstraints().add(column);
        }
    }

    public void initRows(int rows){
        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(row);
        }
    }

    public void initBoard(int gridSize){
        //temporary
        int columns = gridSize;
        int rows = gridSize;

        Field[][] fields = boggle.getGrid();

        for(int x = 0; x < fields.length; x++) {
            for(int y = 0; y < fields[x].length; y++) {
                Pane pane = new Pane();
                pane.setStyle(
                        "-fx-background-color:white;" +
                                "-fx-border-color: black;" +
                                "-fx-border-width: 1 1 1 1;");

                Label label = new Label(fields[x][y].getValue());

                pane.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)->{
                    label.setLayoutX(20);
                    double lowestValue = Math.min(pane.getWidth(), pane.getHeight());
                    label.setFont(Font.font(lowestValue/1.5));
                });
                pane.heightProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)->{
                    label.setLayoutX(20);
                    double lowestValue = Math.min(pane.getWidth(), pane.getHeight());
                    label.setFont(Font.font(lowestValue/1.5));
                });

                pane.getChildren().add(label);
                gridPane.add(pane, y, x);

            }
        }
    }

    /**
     * Initalize the GridPane
     * @param gridSize
     */
    public void init(int gridSize){
        initColumns(gridSize);
        initRows(gridSize);
        initBoard(gridSize);
    }
}

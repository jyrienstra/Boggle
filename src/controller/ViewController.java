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
        //Textfield is a representation, can't be edited by the user
//        currentWordField.setEditable(false);
//        currentWordField.setMouseTransparent(true);
//        currentWordField.setFocusTraversable(false);
//        //TextArea is a representation of the found words, so not editable by user
        foundWordsField.setEditable(false);
        foundWordsField.setMouseTransparent(true);
        foundWordsField.setFocusTraversable(false);
//        //TextArea is a representation of the found words, so not editable by user
//        currentAllowedWordsField.setEditable(false);
//        currentAllowedWordsField.setFocusTraversable(false);


        boggle = new Boggle(gridSize);
        boggle.printGrid();

        //Choose wordlist from textfile(seperated by line)
        chooseFile = new ChooseFile();
        File file = new File("src/wordlist.txt"); //set default file to dutch
        chooseFile.setFile(file);
        this.wordList = chooseFile.getChosenFileInList();

        //Current selected panes are stored in this list
        //this.selectedPanes = new ArrayList<>();

        //Clicked panes are stored in order in this stack
        //this.clickedPaneStack = new Stack<>();

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
     * Call this when u want to change the current word list
     * Uses a .txt file where each new line is a new word
     */
    public void changeWordList(){
        chooseFile = new ChooseFile();
        chooseFile.openFile();
        this.wordList = chooseFile.getChosenFileInList();
        System.out.println(wordList);
    }

    public void showWordList(){
        //Add allowed words to textarea
        currentAllowedWordsField.clear();
        addAllowedWordsToTextArea(currentAllowedWordsField);
    }

    /**
     * Add current allowed words to textarea
     */
    public void addAllowedWordsToTextArea(TextArea textArea){
        textArea.appendText("Allowed words:");
        for(String allowedWord : wordList){
            textArea.appendText("\n" + allowedWord);
        }
    }

    /**
     * Submit a word and check if this word is in our wordlist
     */
    public void submitWord(){
        String currentWord = currentWordField.getText();
        if(wordList.contains(currentWord)){
            //word is in our wordlist
            System.out.println("A new word is found!");
            wordsFound++;
            //Delete found word from wordlist so it can't be found again
            wordList.remove(currentWord);
            //Add found word to our found word textarea
            foundWordsField.appendText("\n" + currentWord);

        }else{
            System.out.println("This word does not exist in our wordpage");
        }
    }

    /**
     * Get a random Character from the alphabet
     * @return A random Character from the alphabet
     */
    public char getRandomCharacter(){
        //Make certain letters more frequent so it's easyer to make words
        int chance = 1 + (int)(Math.random() * 1000);
        if(chance > 500){
            //Make these chars more frequent
            String letterRange1 = "aeiuo"; //klinkers
            Random random = new Random();
            return letterRange1.charAt(random.nextInt(letterRange1.length()));
        }else{
            String letterRange2 = "bcdfghjklmnpqrstvwxyz"; //medeklinkers
            Random random = new Random();
            return letterRange2.charAt(random.nextInt(letterRange2.length()));
        }
    }

    /**
     * Return the current selected word
     * @return
     */
    public String getCurrentWord(){
        String currentWord = "";
        for(Pane pane: selectedPanes){
            //get label
            for(Node node: pane.getChildren()) {
                if (node instanceof Label) {
                    //Get the label content
                    Label currentLabel = (Label) node;
                    currentWord = currentWord + currentLabel.getText();
                }
            }
        }
        return currentWord;
    }

    //We know that a pane in this case is always 'unique'
    public boolean isAlreadySelected(Pane currentPane){
        //Check if pane is already selected
        for(Pane p: selectedPanes){
            if(currentPane.equals(p)){
                return true;
            }
        }
        return false;
    }

    public void selectItem(Pane pane){
        this.clickedPaneColor -= 15;
        //Color pane red when selected
        pane.setStyle(
                "-fx-border-color:black;"+
                "-fx-background-color: rgb(" + this.clickedPaneColor + ", 0, 0)");
        //Add to selected collection
        selectedPanes.add(pane);
    }

    /**
     * Insert new values in the board
     */
    public void newBoard() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Pane) {
                for (Node n : ((Pane) node).getChildren()) {
                    if (n instanceof Label) {
                        Label currentLabel = (Label) n;
                        currentLabel.setText(String.valueOf(getRandomCharacter()));
                    }
                }
            }
        }
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

    public void deselectItem(Pane pane){
        this.clickedPaneColor += 15;

        //Color pane back to original colors
        pane.setStyle(
                "-fx-border-color:black;"+
                        "-fx-background-color:white;");
        //Add to selected collection
        selectedPanes.remove(pane);
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

        Field[][] fields = boggle.getFields();

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

    public void clickPane(Pane pane){
        //add the pane to the stack
        if(clickedPaneStack.empty()) {
            clickedPaneStack.push(pane);
            selectItem(pane);
        }
        else if(isAlreadySelected(pane) && clickedPaneStack.peek() == pane) {
            deselectItem(pane);
            clickedPaneStack.pop();
        }
        else {
            //check currentpane with last pane added to the stack
            if(isNeighbor(pane)){
                clickedPaneStack.push(pane);
                selectItem(pane);
            }
        }
    }

    /**
     * Check if the pane is allowed to be clicked
     * @param currentClickedPane
     */
    public boolean isNeighbor(Pane currentClickedPane){
        if(Math.abs(GridPane.getRowIndex(clickedPaneStack.peek()).intValue() - GridPane.getRowIndex(currentClickedPane).intValue()) <= 1 &&
                Math.abs(GridPane.getColumnIndex(clickedPaneStack.peek()).intValue() - GridPane.getColumnIndex(currentClickedPane).intValue()) <= 1 &&
                !isAlreadySelected(currentClickedPane)) {

            return true;
        }
        return false;
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

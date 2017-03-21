package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jouke on 21-3-2017.
 */
public class Boggle {
    Field[][] grid;
    int rows;
    int cols;
    Dictionary dictionary = new Dictionary(new File("src/wordlist.txt"));
    ArrayList<String> wordList = dictionary.getWordList();

    public Boggle(int rowSize, int colSize){
        this.rows = rowSize;
        this.cols = colSize;

        grid = new Field[rowSize][colSize];
        for(int row = 0; row < rowSize; row++){
            for(int col = 0; col < colSize; col++){
                grid[row][col] = new Field();
            }
        }
        findNeighbors(rowSize, colSize);
    }

    public Boggle(int gridSize){
       this(gridSize, gridSize);
    }

    /**
     * Get the current grid array
     * @return Field[][]
     */
    public Field[][] getGrid(){
        return grid;
    }

    /**
     * Find all neighbors in the grid
     * Only run this once to find all neighbors
     * @todo maybe use a set? field never has a duplicate then, or reset the fields neighbor list after recalling this method
     * @param rowSize
     * @param colSize
     */
    public void findNeighbors(int rowSize, int colSize){
        for(int row = 0; row < rowSize; row++){
            for(int col = 0; col < colSize; col++){
                Field currentField = grid[row][col];

                //Top
                if(row-1 >= 0 && grid[row-1][col] != null){
                    currentField.addNeighbor(grid[row-1][col]);
                }
                //Right top
                if(row-1 >= 0 && col+1 < colSize && grid[row-1][col+1] != null){
                    currentField.addNeighbor(grid[row-1][col+1]);
                }
                //Left top
                if(row-1 >=0 && col-1 >=0 && grid[row-1][col-1] != null){
                    currentField.addNeighbor(grid[row-1][col-1]);
                }
                //Left
                if(col-1 >= 0 && grid[row][col-1] != null){
                    currentField.addNeighbor(grid[row][col-1]);
                }
                //Right
                if(col+1 < colSize && grid[row][col+1] != null){
                    currentField.addNeighbor(grid[row][col+1]);
                }
                //Under
                if(row+1 < rowSize && grid[row+1][col] != null){
                    currentField.addNeighbor(grid[row+1][col]);
                }
                //Right under
                if(row+1 < rowSize && col+1 < colSize && grid[row+1][col+1] !=null){
                    currentField.addNeighbor(grid[row+1][col+1]);
                }
                //Left under
                if(row+1 < rowSize && col-1 >= 0 && grid[row+1][col-1] != null){
                    currentField.addNeighbor(grid[row+1][col-1]);
                }
            }
        }
    }

    /**
     * Find all possible words in the current grid
     */
    public void solveGrid(){

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                Field currentField = grid[row][col];

                findWord(currentField, currentField.getValue());
            }
        }
    }

    public void findWord(Field currentField, String currentString){
        //Elke veld heeft een buur
        //Stopconditie anders stackoverflow error
        //

        if(wordList.contains(currentString)){
            System.out.println("Found word:" + currentString);
        }

        for(Field neighbor: currentField.getNeighborList()){
            String newString = currentString + neighbor.getValue();
            System.out.println("Found word:" + newString);
//            findWord(neighbor, newString);
        }
    }

    /**
     * Debugging purposes
     */
    public void printGrid(){
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                System.out.print(grid[row][col].getValue() + " ");
            }
            System.out.println("");
        }
    }

    /**
     * Debugging purposes
     */
    public void printNeighbors(){
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                Field currentField = grid[row][col];

                System.out.print(currentField.getValue() + " neighbors: ");
                for(Field neighbor: currentField.getNeighborList()){
                    System.out.print(neighbor.getValue() + ",");
                }
                System.out.println();
            }
        }
    }
}
